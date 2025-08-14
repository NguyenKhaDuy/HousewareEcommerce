package com.example.housewareecommerce.Repository.Custom.Impl;

import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import com.example.housewareecommerce.Repository.Custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @Autowired
    ModelMapper modelMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<ProductEntity> searchProduct(Pageable pageable, String nameProduct) {
        StringBuilder sql = new StringBuilder("SELECT * FROM product JOIN category ON category.id = product.categoryid JOIN status ON status.id = product.statusid WHERE product.nameproduct LIKE '%" + nameProduct + "%'");
        String sqlCount = "SELECT count(*) FROM product JOIN category ON category.id = product.categoryid JOIN status ON status.id = product.statusid WHERE product.nameproduct LIKE '%" + nameProduct + "%'";
        Number total = count(sqlCount);
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setFirstResult((int)pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(query.getResultList(), pageable, total.longValue());
    }

    public Number count(String sql){
        Query query = entityManager.createNativeQuery(sql);
        Number total = (Number) query.getSingleResult();
        return total;
    }
}
