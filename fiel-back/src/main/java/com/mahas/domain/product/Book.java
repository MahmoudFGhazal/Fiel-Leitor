package com.mahas.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mahas.domain.DomainEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Column(name = "bok_author", nullable = false)
    private String author;

    @Column(name = "bok_publisher", nullable = false)
    private String publisher;

    @Column(name = "bok_edition", nullable = false)
    private String edition;

    @Column(name = "bok_year", nullable = false)
    private Integer year;

    @Column(name = "bok_isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "bok_barcode", nullable = false, unique = true)
    private String barcode;

    @Column(name = "bok_synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "bok_pages", nullable = false)
    private Integer pages;

    @Column(name = "bok_height_cm")
    private BigDecimal height;

    @Column(name = "bok_width_cm")
    private BigDecimal width;

    @Column(name = "bok_depth_cm")
    private BigDecimal depth;

    @Column(name = "bok_weight_kg")
    private BigDecimal weight;

    @Column(name = "bok_price", nullable = false)
    private BigDecimal price;

    @Column(name = "bok_stock")
    private Integer stock = 0;

    @ManyToOne
    @JoinColumn(name = "bok_prg_id", nullable = false)
    private PriceGroup priceGroup;

    // N:N — categorias
    @ManyToMany
    @JoinTable(
        name = "books_categories",
        joinColumns = @JoinColumn(name = "bca_bok_id"),
        inverseJoinColumns = @JoinColumn(name = "bca_cat_id")
    )
    private List<Category> categories = new ArrayList<>();

    @Column(name = "bok_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean active = true;

    @Column(name = "bok_is_delete", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isDelete;

    @Column(name = "bok_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "bok_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "bok_published_at", nullable = false)
    private LocalDateTime publishedAt;

    @PrePersist
    protected void onCreate() {
        if (isDelete == null) isDelete = false;
        createdAt = updatedAt = publishedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

