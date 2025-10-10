package com.mahas.domain.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.product.Book;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

    @EmbeddedId
    private SaleBookId id;

    @MapsId("saleId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "sbo_sal_id", referencedColumnName = "sal_id", nullable = false)
    private Sale sale;

    @MapsId("bookId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "sbo_bok_id", referencedColumnName = "bok_id", nullable = false)
    private Book book;

    @Column(name = "sbo_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "sbo_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

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
