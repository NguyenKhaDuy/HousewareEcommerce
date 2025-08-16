package com.example.housewareecommerce.Repository.Custom;

import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepositoryCustom {
    Integer averageRating(Long productId);
}
