package com.example.housewareecommerce.Repository.Custom.Impl;

import com.example.housewareecommerce.Entity.EvaluateEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Repository.Custom.EvaluateRepositoryCustom;
import com.example.housewareecommerce.Repository.EvaluateRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EvaluateRepositoryCustomImpl implements EvaluateRepositoryCustom {
    @Autowired
    EvaluateRepository evaluateRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Integer averageRating(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).get();
        List<EvaluateEntity> evaluateEntities = evaluateRepository.findByProductEntity(productEntity);
        Integer sumStar = 0;
        for (EvaluateEntity it : evaluateEntities){
            sumStar += it.getStar();
        }
        Integer result = sumStar / evaluateEntities.size();
        return result;
    }
}
