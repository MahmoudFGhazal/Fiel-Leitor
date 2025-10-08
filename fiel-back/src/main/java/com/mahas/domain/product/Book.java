package com.mahas.domain.product;

import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bok_id")
    private Integer id;

    @Column(name = "bok_name", nullable = false)
    private String name;

    @Column(name = "bok_price", nullable = false)
    private Double price;

    @Column(name = "bok_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean active = false;

    @Column(name = "bok_stock")
    private Integer stock = 0;

    @ManyToOne
    @JoinColumn(name = "bok_cat_id", nullable = false)
    private Category category;

    @Column(name = "bok_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "bok_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "bok_published_at", nullable = false)
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
