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
public class SaleBookId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "sbo_sal_id", nullable = false)
    private Integer saleId;

    @Column(name = "sbo_bok_id", nullable = false)
    private Integer bookId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleBookId)) return false;
        SaleBookId that = (SaleBookId) o;
        return Objects.equals(saleId, that.saleId) &&
               Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, bookId);
    }
}