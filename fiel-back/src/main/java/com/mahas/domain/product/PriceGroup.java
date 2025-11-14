package com.mahas.domain.product;

import com.mahas.domain.DomainEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "price_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceGroup extends DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prg_id")
    private Integer id;

    @Column(name = "prg_name", nullable = false)
    private String name;

    @Column(name = "prg_margin_pct", nullable = false)
    private Double marginPct;

    @Column(name = "prg_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean active = true;

    @Column(name = "prg_created_at", nullable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "prg_updated_at", nullable = false)
    private java.time.LocalDateTime updatedAt;

    @Column(name = "prg_published_at", nullable = false)
    private java.time.LocalDateTime publishedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = publishedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}