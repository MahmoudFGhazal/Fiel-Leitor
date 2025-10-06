package com.mahas.domain.sale;

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
@Table(name = "trader_coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraderCoupon extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tco_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tco_sal_id", nullable = false)
    private Sale sale;

    @Column(name = "tco_value", nullable = false)
    private Double value;

    @Column(name = "tco_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "tco_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "tco_published_at", nullable = false)
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
