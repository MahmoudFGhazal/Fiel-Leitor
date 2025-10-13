package com.mahas.dao.sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Book;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.SaleBook;
import com.mahas.domain.sale.SaleBookId;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

@Component
public class SaleBookDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof SaleBook)) return null;

        SaleBook sb = (SaleBook) entity;

        StringBuilder jpql = new StringBuilder("SELECT sb FROM SalesBook sb WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(sb) FROM SalesBook sb WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (sb.getSale() != null && sb.getSale().getId() != null) { 
            where.append(" AND sb.sale.id = :saleId"); 
            params.put("saleId", sb.getSale().getId()); 
        }
        if (sb.getBook() != null && sb.getBook().getId() != null) { 
            where.append(" AND sb.book.id = :bookId"); 
            params.put("bookId", sb.getBook().getId()); 
        }

        jpql.append(where); 
        countJpql.append(where);

        int page = request.getPage(); 
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<SaleBook> query = entityManager.createQuery(jpql.toString(), SaleBook.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<SaleBook> resultList = query.getResultList();

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
    public SQLResponse save(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof SaleBook)) {
            return null;
        }

        SaleBook saleBook = (SaleBook) entity;

        try {
            ensureEmbeddedId(saleBook);

            if (saleBook.getSale() != null && saleBook.getSale().getId() != null) {
                saleBook.setSale(entityManager.getReference(Sale.class, saleBook.getSale().getId()));
            }
            if (saleBook.getBook() != null && saleBook.getBook().getId() != null) {
                saleBook.setBook(entityManager.getReference(Book.class, saleBook.getBook().getId()));
            }

            entityManager.persist(saleBook);
            entityManager.flush();

            response.setEntity(saleBook);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    private void ensureEmbeddedId(SaleBook saleCard) {
        if (saleCard.getId() == null) {
            saleCard.setId(new SaleBookId());
        }

        if (saleCard.getSale() != null && saleCard.getSale().getId() != null) {
            saleCard.getId().setSaleId(saleCard.getSale().getId());
        }
        
        if (saleCard.getBook() != null && saleCard.getBook().getId() != null) {
            saleCard.getId().setBookId(saleCard.getBook().getId());
        }
    }
}
