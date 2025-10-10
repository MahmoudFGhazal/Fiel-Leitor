package com.mahas.domain.sale;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleCardId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "sca_sal_id", nullable = false)
    private Integer saleId;

    @Column(name = "sca_car_id", nullable = false)
    private Integer cardId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleCardId)) return false;
        SaleCardId that = (SaleCardId) o;
        return Objects.equals(saleId, that.saleId) &&
               Objects.equals(cardId, that.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, cardId);
    }
}