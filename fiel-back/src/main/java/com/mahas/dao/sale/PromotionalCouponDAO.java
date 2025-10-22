package com.mahas.dao.sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.sale.PromotionalCoupon;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

@Component
public class PromotionalCouponDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof PromotionalCoupon)) return null;

        PromotionalCoupon coupon = (PromotionalCoupon) entity;

        StringBuilder jpql = new StringBuilder("SELECT p FROM PromotionalCoupon p WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(p) FROM PromotionalCoupon p WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (coupon.getId() != null) { 
            where.append(" AND p.id = :id"); 
            params.put("id", coupon.getId()); 
        }
        if (coupon.getCode() != null) { 
            where.append(" AND p.code = :code"); 
            params.put("code", coupon.getCode()); 
        }
        if (coupon.getUsed() != null) { 
            where.append(" AND p.used = :used"); 
            params.put("used", coupon.getUsed()); 
        }

        jpql.append(where); 
        countJpql.append(where);

        int page = request.getPage(); 
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<PromotionalCoupon> query = entityManager.createQuery(jpql.toString(), PromotionalCoupon.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<PromotionalCoupon> resultList = query.getResultList();

        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            countQuery.setParameter(e.getKey(), e.getValue());
        }

        long totalItems = countQuery.getSingleResult();
        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (limit == 1) response.setEntity(resultList.get(0));
            else response.setEntities(new ArrayList<>(resultList));
        }

        response.setPage(page); 
        response.setLimit(limit);
        response.setTotalItem((int) totalItems); 
        response.setTotalPage(totalPage);
        
        return response;
    }

    @Override
    public SQLResponse update(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof PromotionalCoupon)){
            return null;
        }

        PromotionalCoupon promotionalCoupon = (PromotionalCoupon) entity;

        try {
            System.out.println(promotionalCoupon.getId());
            PromotionalCoupon existingPromotionalCoupon = entityManager.find(PromotionalCoupon.class, promotionalCoupon.getId());
            if (existingPromotionalCoupon == null) {
                response.setEntity(null);
                return response;
            }
            
            if (promotionalCoupon.getUsed() != null) {
                existingPromotionalCoupon.setUsed(promotionalCoupon.getUsed());
            }
           
            entityManager.flush();

            response.setEntity(existingPromotionalCoupon);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }
}
