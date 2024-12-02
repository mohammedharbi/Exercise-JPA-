package com.example.amazoncloneupdated.Service;

import com.example.amazoncloneupdated.Model.Merchant;
import com.example.amazoncloneupdated.Model.MerchantStock;
import com.example.amazoncloneupdated.Model.Product;
import com.example.amazoncloneupdated.Repository.MerchantRepository;
import com.example.amazoncloneupdated.Repository.MerchantStockRepository;
import com.example.amazoncloneupdated.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;
    private final MerchantStockRepository merchantStockRepository;
    private final MerchantService merchantService;
    private final ProductService productService;

    public List<MerchantStock> getMerchantss() {
        return merchantStockRepository.findAll();
    }
    public String addMerchantStock(MerchantStock merchantStock) {
        String checker= "";
        for (Merchant m: merchantRepository.findAll()) {
            if (m.getId().equals(merchantStock.getMerchantid())) {
                checker += "m";
            }
        }
        for (Product p: productRepository.findAll()) {
            if (p.getId().equals(merchantStock.getProductId())) {
                checker += "p";
            }
        }
        if (checker.equals("mp")) {
        merchantStockRepository.save(merchantStock);
        return "done";
        }else return checker;
    }

    public String updateMerchantStock(Integer id,MerchantStock merchantStock) {
        MerchantStock oldMerchantStock = merchantStockRepository.findById(id).get();
        String checker = "";
        if (oldMerchantStock == null) {
            return checker;
        } else {
            for (Merchant m : merchantRepository.findAll()) {
                if (m.getId().equals(merchantStock.getMerchantid())) {
                    checker += "m";
                }
            }
            for (Product p : productRepository.findAll()) {
                if (p.getId().equals(merchantStock.getProductId())) {
                    checker += "p";
                }
            }
            if (checker.equals("mp")) {
                for (MerchantStock m : merchantStockRepository.findAll()) {
                    if (m.getId().equals(id)) {
                        oldMerchantStock.setMerchantid(m.getMerchantid());
                        oldMerchantStock.setProductId(m.getProductId());
                        oldMerchantStock.setStock(m.getStock());
                        return "done";
                    }
                }
            }
            return checker;
        }
    }

    public boolean deleteMerchantStock(Integer id) {
        MerchantStock oldMerchantStock = merchantStockRepository.findById(id).get();


        if (oldMerchantStock == null) {return false;
        }else {
            merchantStockRepository.delete(oldMerchantStock);
            return true;
        }
    }

    //Create endpoint where merchant can add more stocks of product to a
    //merchant
    //Stock
    //• this endpoint should accept a product id and merchant id and the amount of additional
    //stock.

    public boolean addMoreStocks(Integer merchantid, Integer productId, Integer stock){
        for (MerchantStock m: merchantStockRepository.findAll()) {
            if (m.getId().equals(merchantid)) {
                if (m.getProductId().equals(productId)) {
                    m.setStock(stock);
                    merchantStockRepository.save(m);
                    return true;
                }
            }
        }
        return false;
        }
    }


