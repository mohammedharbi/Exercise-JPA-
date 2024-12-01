package com.example.amazoncloneupdated.Service;

import com.example.amazoncloneupdated.Model.*;
import com.example.amazoncloneupdated.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;
    private final MerchantStockRepository merchantStockRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final GeneratedGiftsCardsRepository generatedGiftsCardsRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean updateUser(Integer id,User user) {
        User oldUser = userRepository.findById(id).get();

        if (oldUser == null) {
            return false;
        } else {
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            oldUser.setBalance(user.getBalance());
            oldUser.setRole(user.getRole());
            userRepository.save(oldUser);
            return true;
        }
    }

    public boolean removeUser(Integer id) {

        User oldUser = userRepository.findById(id).get();


        if (oldUser == null) {return false;
        }else {
            userRepository.delete(oldUser);
            return true;
        }
    }



    /*
Create endpoint where user can buy a product directly
• this endpoint should accept user id, product id, merchant id.
• check if all the given ids are valid or not
• check if the merchant has the product in stock or return bad request.
• reduce the stock from the MerchantStock.
• deducted the price of the product from the user balance.
• if balance is less than the product price returns bad request.
     */

    public String buyProduct(Integer userid, Integer productid, Integer merchantid) {
        String result = "";
        double price = 0.0;
        MerchantStock stock = new MerchantStock();

        // Fetch user, product, and merchant stock
        User user = userRepository.findById(userid).orElse(null);
        Product product = productRepository.findById(productid).orElse(null);
        Merchant merchant = merchantRepository.findById(merchantid).orElse(null);

        // Validate input entities
        if (user == null) {
            return "User not found";
        }
        if (product == null) {
            return "Product not found";
        }
        if (merchant == null) {
            return "Merchant stock not found for this product";
        }

        for (MerchantStock m : merchantStockRepository.findAll()) {
            if (user.getId().equals(userid)){
                if (product.getId().equals(product.getId())) {
                    if (merchant.getId().equals(merchant.getId())) {
                         stock = m;
                    }
                }
            }
        }

        // Check if the user has enough balance and if there is stock
        if (user.getBalance() < product.getPrice()) {
            return "Insufficient balance";
        }
        if (stock.getStock() <= 0) {
            return "Out of stock";
        }

        // Process the purchase
        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        // Save changes
        merchantStockRepository.save(stock);
        userRepository.save(user);

        // Create purchase history
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setAmount(product.getPrice());
        purchaseHistory.setUserid(userid);
        purchaseHistoryRepository.save(purchaseHistory);
        // Return success message
        return "Purchase successful";
    }

    //ex 1
    public User luckyDrawCashBack(Integer userid){
        Random random = new Random();

        // if the user is an admin
        boolean isAdmin = false;
        User adminUser = userRepository.findById(userid).orElse(null);
        if (adminUser != null && "Admin".equals(adminUser.getRole())) {
            isAdmin = true;
        }

        if (!isAdmin) {
            return null; // only admin
        }

        // Get all purchase histories
        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAll();
        if (purchaseHistories.isEmpty()) {
            return null;
        }

        // Select a random winner from purchase histories
        int randomIndex = random.nextInt(purchaseHistories.size());
        PurchaseHistory luckyWinnerHistory = purchaseHistories.get(randomIndex);

        // find the min
        double minAmount = 999999;
        for (PurchaseHistory purchase : purchaseHistories) {
            if (purchase.getAmount() < minAmount) {
                minAmount = purchase.getAmount();
            }
        }

        // find the winner user
        User luckyWinner = userRepository.findById(luckyWinnerHistory.getUserid()).orElse(null);
        if (luckyWinner != null) {
            // add the cashback to the user balance
            luckyWinner.setBalance(luckyWinner.getBalance() + minAmount);
            userRepository.save(luckyWinner);
            return luckyWinner;
        }

        return null;
    }

    //ex2
public int addDiscount(Integer userid,Integer productid, double discount) {
    Product product = productRepository.findById(productid).orElse(null);

    // if the user is an admin
        boolean isAdmin = false;
        User adminUser = userRepository.findById(userid).orElse(null);
        if (adminUser != null && "Admin".equals(adminUser.getRole())) {
            isAdmin = true;
        }

        if (adminUser == null) {return 1;}

        if (!isAdmin) {
            return 2; // only admin
        }
    if (product == null) {
        return 3;
    }

        double discountAmount = (product.getPrice() * discount) / 100;
    product.setPrice(product.getPrice() - discountAmount);
    productRepository.save(product);
        return 0; // product discounted



    }

    //ex3
    public int generateGiftCard(Integer userid,Integer value){
        User oldUser = userRepository.findById(userid).orElse(null);
        if (oldUser == null) {return 1;}

        if (oldUser.getBalance() >= value) {
            oldUser.setBalance(oldUser.getBalance() - value);
            userRepository.save(oldUser);
        }else return 2;

                    String valueStr = String.valueOf(value)+"CE";
                    String randoms = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                    Random random = new Random();
                    StringBuilder randomToCombine = new StringBuilder();

                    for (int i = 0; i < 10; i++) {
                        int index = random.nextInt(randoms.length());
                        randomToCombine.append(randoms.charAt(index));
                    }

                    String code = valueStr + randomToCombine.toString();

                    GeneratedGiftsCards generatedGiftsCards = new GeneratedGiftsCards();
                    generatedGiftsCards.setGift_card_code(code);
                    generatedGiftsCards.setUserid(userid);
                    generatedGiftsCardsRepository.save(generatedGiftsCards);// to add it to the System available gift cards
                                return 0;


    }

    //ex4
    public int redeemGiftCard(Integer userid,String code){
        User oldUser = userRepository.findById(userid).orElse(null);
        if (oldUser == null) {return 1;}

                for (GeneratedGiftsCards c: generatedGiftsCardsRepository.findAll()){
                    if (c.getGift_card_code().equals(code)){
                        int index = code.indexOf("CE");
                        String cardValue = code.substring(0,index);
                        oldUser.setBalance(oldUser.getBalance()+Double.parseDouble(cardValue));
                        generatedGiftsCardsRepository.delete(c);;
                        return 0;
                    }

                }
                return 2;
            }



    //ex5
    public int inviteRewards(Integer newuserid, Integer hostuserid){
        User hostuser = userRepository.findById(hostuserid).orElse(null);
        if (hostuser == null) {return 1;}
        User newuser = userRepository.findById(newuserid).orElse(null);
        if (newuser == null) {return 2;}

        hostuser.setBalance(hostuser.getBalance()+25);
        userRepository.save(hostuser);
        newuser.setBalance(newuser.getBalance()+25);
        userRepository.save(newuser);
        return 0;
                    }




    public void addtoWishlist(String userid,String productid) {}
    public void buyforOthersUserWishlists(String userid,String productid) {}
    public int fivetimesforsamemerchanttogetdiscount(String userid,String productid) {return 25;}
    public void productattimerecnetdiscount(String userid,String productid) {}


}