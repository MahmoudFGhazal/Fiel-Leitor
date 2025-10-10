package com.mahas.domain.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "promotional_coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionalCoupon extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pco_id")
    private Integer id;

    @Column(name = "pco_value", nullable = false)
    private BigDecimal value;

    @Column(name = "pco_used", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean used = false;

    @Column(name = "pco_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "pco_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "pco_published_at", nullable = false)
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
