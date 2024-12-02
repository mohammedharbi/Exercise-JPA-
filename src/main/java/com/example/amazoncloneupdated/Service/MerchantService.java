package com.example.amazoncloneupdated.Service;

import com.example.amazoncloneupdated.Model.Category;
import com.example.amazoncloneupdated.Model.Merchant;
import com.example.amazoncloneupdated.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public List<Merchant> getMerchants() {
        return merchantRepository.findAll();
    }
    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public boolean updateMerchant(Integer id,Merchant merchant) {

        Merchant oldMerchant = merchantRepository.findById(id).get();

        if (oldMerchant == null) {
            return false;
        } else {
            oldMerchant.setName(oldMerchant.getName());
            merchantRepository.save(oldMerchant);
            return true;
        }
    }

    public boolean deleteMerchant(Integer id) {
            Merchant oldMerchant = merchantRepository.findById(id).get();


            if (oldMerchant == null) {return false;
            }else {
                merchantRepository.delete(oldMerchant);
                return true;
            }
    }


}
