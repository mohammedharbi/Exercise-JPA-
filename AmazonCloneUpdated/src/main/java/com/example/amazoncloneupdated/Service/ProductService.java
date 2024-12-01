package com.example.amazoncloneupdated.Service;

import com.example.amazoncloneupdated.Model.Category;
import com.example.amazoncloneupdated.Model.MerchantStock;
import com.example.amazoncloneupdated.Model.Product;
import com.example.amazoncloneupdated.Repository.CategoryRepository;
import com.example.amazoncloneupdated.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    public boolean addProduct(Product product) {
        boolean categoryidvalid = false;

        for (Category c : categoryRepository.findAll()) {
            if (c.getId().equals(product.getCategoryid())){
                categoryidvalid = true;
            }
        }
        if (categoryidvalid){
            productRepository.save(product);
        return true;
        }else return false;
    }
    public boolean updateProduct(Integer id,Product product) {
        boolean categoryidvalid = false;
        Product oldProduct = productRepository.findById(id).get();
        for (Category c : categoryRepository.findAll()) {
            if (c.getId().equals(product.getCategoryid())) {
                categoryidvalid = true;
            }
        }

        if (categoryidvalid) {
            for (Product p : productRepository.findAll()) {
                if (p.getId().equals(id)) {
                    oldProduct.setCategoryid(product.getCategoryid());
                    oldProduct.setName(product.getName());
                    oldProduct.setPrice(product.getPrice());
                    productRepository.save(oldProduct);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteProduct(Integer id) {

        Product oldProduct = productRepository.findById(id).get();


        if (oldProduct == null) {return false;
        }else {
            productRepository.delete(oldProduct);
            return true;
        }
    }


}
