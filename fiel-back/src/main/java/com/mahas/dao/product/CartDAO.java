package com.mahas.dao.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Book;
import com.mahas.domain.product.Cart;
import com.mahas.domain.product.CartId;
import com.mahas.domain.product.Category;
import com.mahas.domain.user.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class CartDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Cart)) return null;

        Cart cart = (Cart) entity;

        StringBuilder jpql = new StringBuilder("SELECT c FROM Cart c WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(c) FROM Cart c WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (cart.getUser() != null) { 
            where.append(" AND c.user = :user"); 
            params.put("user", cart.getUser()); 
        }

        if (cart.getBook() != null) { 
            where.append(" AND c.book = :book"); 
            params.put("book", cart.getBook()); 
        }

        jpql.append(where); countJpql.append(where);

        int page = request.getPage(); int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        Query query = entityManager.createQuery(jpql.toString(), Cart.class);
        params.forEach(query::setParameter);
        if (limit > 0) { query.setFirstResult(offset); query.setMaxResults(limit); }

        List<Category> resultList = query.getResultList();
        Query countQuery = entityManager.createQuery(countJpql.toString());
        params.forEach(countQuery::setParameter);
        int totalItems = ((Number) countQuery.getSingleResult()).intValue();
        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (limit == 1) response.setEntity(resultList.get(0));
            else response.setEntities(new ArrayList<>(resultList));
        }

        response.setPage(page); response.setLimit(limit);
        response.setTotalItem(totalItems); response.setTotalPage(totalPage);
        return response;
    }

    @Override
    public SQLResponse save(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Cart)) {
            return null;
        }

        Cart cart = (Cart) entity;

        try {
            ensureEmbeddedId(cart);

            if (cart.getUser() != null && cart.getUser().getId() != null) {
                cart.setUser(entityManager.getReference(User.class, cart.getUser().getId()));
            }
            if (cart.getBook() != null && cart.getBook().getId() != null) {
                cart.setBook(entityManager.getReference(Book.class, cart.getBook().getId()));
            }

            entityManager.persist(cart);
            entityManager.flush();

            response.setEntity(cart);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse delete(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Cart)) {
            return null;
        }

        Cart cart = (Cart) entity;

        CartId id = resolveId(cart);
        if (id == null || id.getUserId() == null || id.getBookId() == null) {
            response.setEntity(null);
            return response;
        }

        Cart managed = entityManager.find(Cart.class, id);
        if (managed == null) {
            response.setEntity(null);
            return response;
        }

        entityManager.remove(managed);
        entityManager.flush();

        response.setEntity(managed);
        return response;
    }
    
    @Override
    public SQLResponse update(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof Cart)){
            return null;
        }

        Cart cart = (Cart) entity;

        try {
            CartId id = resolveId(cart);
            if (id == null || id.getUserId() == null || id.getBookId() == null) {
                response.setEntity(null);
                return response;
            }

            Cart existingCart = entityManager.find(Cart.class, id);
            if (existingCart == null) {
                response.setEntity(null);
                return response;
            }

            if (cart.getQuantity() != null) {
                existingCart.setQuantity(cart.getQuantity());
            }

            entityManager.flush();

            response.setEntity(existingCart);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    private void ensureEmbeddedId(Cart cart) {
        if (cart.getId() == null) {
            cart.setId(new CartId());
        }

        if (cart.getUser() != null && cart.getUser().getId() != null) {
            cart.getId().setUserId(cart.getUser().getId());
        }
        
        if (cart.getBook() != null && cart.getBook().getId() != null) {
            cart.getId().setBookId(cart.getBook().getId());
        }
    }

    private CartId resolveId(Cart cart) {
        if (cart.getId() != null && cart.getId().getUserId() != null && cart.getId().getBookId() != null) {
            return cart.getId();
        }
        CartId derived = new CartId();
        boolean hasAny = false;

        if (cart.getId() != null) {
            if (cart.getId().getUserId() != null) {
                derived.setUserId(cart.getId().getUserId());
                hasAny = true;
            }
            if (cart.getId().getBookId() != null) {
                derived.setBookId(cart.getId().getBookId());
                hasAny = true;
            }
        }

        if (cart.getUser() != null && cart.getUser().getId() != null) {
            derived.setUserId(cart.getUser().getId());
            hasAny = true;
        }

        if (cart.getBook() != null && cart.getBook().getId() != null) {
            derived.setBookId(cart.getBook().getId());
            hasAny = true;
        }

        return hasAny ? derived : null;
    }
}
