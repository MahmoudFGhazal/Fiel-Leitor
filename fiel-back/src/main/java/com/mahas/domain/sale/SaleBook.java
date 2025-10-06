package com.mahas.domain.sale;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.product.Book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sales_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleBook extends DomainEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "sbo_sal_id", nullable = false)
    private Sale sale;

    @Id
    @ManyToOne
    @JoinColumn(name = "sbo_bok_id", nullable = false)
    private Book book;

    @Column(name = "sbo_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "sbo_price", nullable = false)
    private Double price;

    @Column(name = "sbo_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sbo_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "sbo_published_at", nullable = false)
    private LocalDateTime publishedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = publishedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class SalesBookId implements Serializable {
    private Integer sale;
    private Integer book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesBookId)) return false;
        SalesBookId that = (SalesBookId) o;
        return Objects.equals(sale, that.sale) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, book);
    }
}