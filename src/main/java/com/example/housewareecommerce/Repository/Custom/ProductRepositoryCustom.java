package com.example.housewareecommerce.Repository.Custom;

import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustom {
    Page<ProductEntity> searchProduct(Pageable pageable, String nameProduct);
}
