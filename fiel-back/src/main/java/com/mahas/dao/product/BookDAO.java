package com.mahas.dao.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Book;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Component
public class BookDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof Book)) {
            return null;
        }

        Book book = (Book) entity;

        StringBuilder jpql = new StringBuilder("SELECT b FROM Book b WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(b) FROM Book b WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (book.getId() != null) {
            whereClause.append(" AND b.id = :id");
            parameters.put("id", book.getId());
        }
        if (book.getName() != null && !book.getName().isEmpty()) {
            whereClause.append(" AND LOWER(b.name) LIKE LOWER(:name)");
            parameters.put("name", "%" + book.getName() + "%");
        }
        if (book.getCategory() != null && book.getCategory().getId() != null) {
            whereClause.append(" AND b.category.id = :catId");
            parameters.put("catId", book.getCategory().getId());
        }
        if (book.getActive() != null) {
            whereClause.append(" AND b.active = :active");
            parameters.put("active", book.getActive());
        }
        whereClause.append(" AND b.isDelete = :isDelete");
        parameters.put("isDelete", false);


        jpql.append(whereClause);
        countJpql.append(whereClause);

        int page = request.getPage();
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<Book> query = entityManager.createQuery(jpql.toString(), Book.class);
        parameters.forEach(query::setParameter);

        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<Book> resultList = query.getResultList();

        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);
        parameters.forEach(countQuery::setParameter);
        long totalItems = countQuery.getSingleResult();

        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (limit == 1) {
                response.setEntity(resultList.get(0));
            } else {
                response.setEntities(new ArrayList<>(resultList));
            }
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
        if(!(entity instanceof Book)){
            return null;
        }

        Book book = (Book) entity;

        try {
            Book existingBook = entityManager.find(Book.class, book.getId());
            if (existingBook == null) {
                response.setEntity(null);
                return response;
            }
            
            if (book.getName() != null) {
                existingBook.setName(book.getName());
            }
            if (book.getActive() != null) {
                existingBook.setActive(book.getActive());
            }
            if (book.getPrice() != null) {
                existingBook.setPrice(book.getPrice());
            }
            if (book.getStock() != null) {
                existingBook.setStock(book.getStock());
            }
            if (book.getCategory() != null) {
                existingBook.setCategory(book.getCategory());
            }
            if (book.getIsDelete() != null) {
                existingBook.setIsDelete(book.getIsDelete());
            }
            
            entityManager.flush();

            response.setEntity(existingBook);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse delete(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Book)) {
            return null;
        }

        Book book = (Book) entity;

        String jpql = "DELETE FROM Book b WHERE b.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", book.getId());

        try {
            int result = query.executeUpdate();
            if (result > 0) {
                response.setEntity(book); 
            }else {
                response.setEntity(null);
            }
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse save(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof Book)){
            return null;
        }

        Book book = (Book) entity;

        try {
            entityManager.persist(book);

            entityManager.flush();

            response.setEntity(book);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

}
