package com.example.housewareecommerce.Repository.Custom.Impl;

import com.example.housewareecommerce.Repository.Custom.StatisticsRepositoryCustom;
import com.example.housewareecommerce.Utils.DateUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class StatisticsRepositoryCustomImpl implements StatisticsRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Integer numberOrderAtCurrentMonth() {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM orders WHERE orders.dateorder >= \"");
        sql.append(DateUtils.getFirstDayOfCurrentMonth() + "\" AND orders.dateorder <= \"");
        sql.append(DateUtils.getLastDayOfCurrentMonth() + "\"");
        Query query = entityManager.createNativeQuery(sql.toString());
        Number result = (Number) query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public Float sumPriceAtCurrentMonth() {
        StringBuilder sql = new StringBuilder("SELECT SUM(orders.totalprice) FROM orders WHERE orders.dateorder >= \"");
        sql.append(DateUtils.getFirstDayOfCurrentMonth() + "\" AND orders.dateorder <= \"");
        sql.append(DateUtils.getLastDayOfCurrentMonth() + "\"");
        Query query = entityManager.createNativeQuery(sql.toString());
        Number result = (Number) query.getSingleResult();
        return result != null ? result.floatValue() : 0;
    }

    @Override
    public Integer numberSuccessfulOrders() {
        StringBuilder sql = new StringBuilder("SELECT SUM(orders.totalprice) FROM orders WHERE orders.dateorder >= \"");
        sql.append(DateUtils.getFirstDayOfCurrentMonth() + "\" AND orders.dateorder <= \"");
        sql.append(DateUtils.getLastDayOfCurrentMonth() + "\" AND statusid = 1");
        Query query = entityManager.createNativeQuery(sql.toString());
        Number result = (Number) query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public Integer numberCancelOrdersAtCurrentMonth() {
        StringBuilder sql = new StringBuilder("SELECT SUM(orders.totalprice) FROM orders WHERE orders.dateorder >= \"");
        sql.append(DateUtils.getFirstDayOfCurrentMonth() + "\" AND orders.dateorder <= \"");
        sql.append(DateUtils.getLastDayOfCurrentMonth() + "\" AND statusid = 2");
        Query query = entityManager.createNativeQuery(sql.toString());
        Number result = (Number) query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public Integer numberShippingOrdersAtCurrentMonth() {
        StringBuilder sql = new StringBuilder("SELECT SUM(orders.totalprice) FROM orders WHERE orders.dateorder >= \"");
        sql.append(DateUtils.getFirstDayOfCurrentMonth() + "\" AND orders.dateorder <= \"");
        sql.append(DateUtils.getLastDayOfCurrentMonth() + "\" AND statusid = 3");
        Query query = entityManager.createNativeQuery(sql.toString());
        Number result = (Number) query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public Integer numberOrderAtCurrentDate() {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM orders WHERE orders.dateorder = \"");
        sql.append(LocalDate.now() + "\"");
        Query query = entityManager.createNativeQuery(sql.toString());
        Number result = (Number) query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public Float sumPriceAtCurrentDate() {
        StringBuilder sql = new StringBuilder("SELECT SUM(orders.totalprice) FROM orders WHERE orders.dateorder >= \"");
        sql.append(LocalDate.now() + "\"");
        Query query = entityManager.createNativeQuery(sql.toString());
        Number result = (Number) query.getSingleResult();
        return result != null ? result.floatValue() : 0;
    }
}
