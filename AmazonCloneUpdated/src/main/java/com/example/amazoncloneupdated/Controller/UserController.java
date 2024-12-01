package com.example.amazoncloneupdated.Controller;

import com.example.amazoncloneupdated.ApiResponse.ApiResponse;
import com.example.amazoncloneupdated.Model.User;
import com.example.amazoncloneupdated.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("user added"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
       boolean isDeleted = userService.removeUser(id);

       if(isDeleted){
           return ResponseEntity.status(201).body(new ApiResponse("user deleted"));
       }else return ResponseEntity.status(400).body(new ApiResponse("user not deleted"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = userService.updateUser(id, user);
        if(isUpdated){
            return ResponseEntity.status(201).body(new ApiResponse("user updated"));
        }else return ResponseEntity.status(400).body(new ApiResponse("user not updated"));
    }

    @PutMapping("/buy-product/{userid}/{productid}/{merchantid}")
    public ResponseEntity buyProduct(@PathVariable Integer userid, @PathVariable Integer productid, @PathVariable Integer merchantid) {
        String checker = userService.buyProduct(userid, productid, merchantid);

        return ResponseEntity.status(201).body(new ApiResponse(checker));
    }

    //ex1
    @PutMapping("/luckyDrawCashBack/{userid}")
    public ResponseEntity luckyDrawCashBack(@PathVariable Integer userid) {
        User u = userService.luckyDrawCashBack(userid);

        if (u != null) {
            return ResponseEntity.status(200).body(u);
        }else return ResponseEntity.status(400).body(new ApiResponse("no cashback for users because there is no buyers saved in the system"));
    }

    //ex2
    @PutMapping("/addDiscount/{userid}/{productid}/{discount}")
    public ResponseEntity addDiscount(@PathVariable Integer userid, @PathVariable Integer productid, @PathVariable Double discount) {
        Integer num = userService.addDiscount(userid,productid,discount);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("discount added"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("user role is not 'Admin"));
            case 3 -> ResponseEntity.status(404).body(new ApiResponse("product id does not exist"));
            default -> ResponseEntity.status(400).body(new ApiResponse("discount not added"));
        };
    }

    @PutMapping("/generateGiftCard/{userid}/{value}")
    public ResponseEntity generateGiftCard(@PathVariable Integer userid, @PathVariable Integer value) {
        int num = userService.generateGiftCard(userid,value);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Gift card created"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("user insufficient balance"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Gift card not created"));
        };

    }

    @PutMapping("/redeemGiftCard/{userid}/{code}")
    public ResponseEntity redeemGiftCard(@PathVariable Integer userid, @PathVariable String code) {
        int num = userService.redeemGiftCard(userid,code);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Gift card redeemed"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("Gift card does not exist"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Gift card not redeemed"));
        };
    }

    @PutMapping("/inviteRewards/{newuserid}/{hostuserid}")
    public ResponseEntity inviteRewards(@PathVariable Integer newuserid, @PathVariable Integer hostuserid) {
        int num = userService.inviteRewards(newuserid,hostuserid);
        return switch (num){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Congrats 25 Riyals added to your account"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse("host user id does not exist"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse("new user if does not exist"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Rewards not added"));
        };
    }


}