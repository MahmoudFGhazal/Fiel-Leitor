package com.mahas.dao.sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.sale.Sale;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Component
public class SaleDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof Sale)) {
            return null;
        }

        Sale sale = (Sale) entity;

        int page  = request.getPage();
        int limit = request.getLimit();
        boolean fetch = (limit == 1);

        StringBuilder jpql = new StringBuilder(
            fetch
            ? "SELECT DISTINCT s FROM Sale s LEFT JOIN FETCH s.saleBooks sb LEFT JOIN FETCH sb.book WHERE 1=1"
            : "SELECT s FROM Sale s WHERE 1=1"
        );
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(s) FROM Sale s WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (sale.getId() != null) {
            where.append(" AND s.id = :id");
            params.put("id", sale.getId());
        }
        if (sale.getUser() != null && sale.getUser().getId() != null) {
            where.append(" AND s.user.id = :userId");
            params.put("userId", sale.getUser().getId());
        }
        if (sale.getStatusSale() != null && sale.getStatusSale().getId() != null) {
            where.append(" AND s.statusSale.id = :statusId");
            params.put("statusId", sale.getStatusSale().getId());
        }

        jpql.append(where);
        countJpql.append(where);

        if (!fetch) {
            jpql.append(" ORDER BY s.id DESC");
        }

        TypedQuery<Sale> query = entityManager.createQuery(jpql.toString(), Sale.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }

        // ✅ Agora o offset só é calculado e usado quando realmente aplicamos paginação
        if (!fetch && limit > 0) {
            int offset = (page > 0) ? (page - 1) * limit : 0;
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<Sale> resultList = query.getResultList();

        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            countQuery.setParameter(e.getKey(), e.getValue());
        }

        long totalItems = countQuery.getSingleResult();
        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (fetch) response.setEntity(resultList.get(0));
            else response.setEntities(new ArrayList<>(resultList));
        }

        response.setPage(page);
        response.setLimit(limit);
        response.setTotalItem((int) totalItems);
        response.setTotalPage(totalPage);

        return response;
    }

    @Override
    public SQLResponse save(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof Sale)){
            return null;
        }

        Sale sale = (Sale) entity;

        try {
            entityManager.persist(sale);

            entityManager.flush();

            response.setEntity(sale);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse delete(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Sale)) {
            return null;
        }

        Sale sale = (Sale) entity;

        String jpql = "DELETE FROM Sale s WHERE s.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", sale.getId());

        try {
            int result = query.executeUpdate();
            if (result > 0) {
                response.setEntity(sale); 
            }else {
                response.setEntity(null);
            }
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse update(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof Sale)){
            return null;
        }

        Sale sale = (Sale) entity;

        try {
            Sale existingSale = entityManager.find(Sale.class, sale.getId());
            if (existingSale == null) {
                response.setEntity(null);
                return response;
            }
            
            if (sale.getAddress() != null) {
                existingSale.setAddress(sale.getAddress());
            }
            if (sale.getDeliveryDate() != null) {
                existingSale.setDeliveryDate(sale.getDeliveryDate());
            }
            if (sale.getPromotionalCoupon() != null) {
                existingSale.setPromotionalCoupon(sale.getPromotionalCoupon());
            }
            if (sale.getTraderCoupons() != null) {
                existingSale.setTraderCoupons(sale.getTraderCoupons());
            }
            if (sale.getStatusSale() != null) {
                existingSale.setStatusSale(sale.getStatusSale());
            }
           
            entityManager.flush();

            response.setEntity(existingSale);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }
}
