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
import com.mahas.domain.sale.SaleCard;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class SaleCardDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof SaleCard)) return null;

        SaleCard sc = (SaleCard) entity;

        StringBuilder jpql = new StringBuilder("SELECT sc FROM SalesCard sc WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(sc) FROM SalesCard sc WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (sc.getSale() != null && sc.getSale().getId() != null) { where.append(" AND sc.sale.id = :saleId"); params.put("saleId", sc.getSale().getId()); }
        if (sc.getCard() != null && sc.getCard().getId() != null) { where.append(" AND sc.card.id = :cardId"); params.put("cardId", sc.getCard().getId()); }

        jpql.append(where); countJpql.append(where);

        int page = request.getPage(); int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        Query query = entityManager.createQuery(jpql.toString(), SaleCard.class);
        params.forEach(query::setParameter);
        if (limit > 0) { query.setFirstResult(offset); query.setMaxResults(limit); }

        List<SaleCard> resultList = query.getResultList();
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
}
