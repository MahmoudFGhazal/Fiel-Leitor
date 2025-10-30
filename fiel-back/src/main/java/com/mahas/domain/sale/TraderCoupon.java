package com.mahas.domain.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tco_origin_sal_id", nullable = false, unique = true)
    private Sale originSale;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "tco_applied_sal_id", nullable = true)
    private Sale appliedSale;

    @ManyToOne
    @JoinColumn(name = "tco_usr_id", nullable = true)
    private User user;

    @Column(name = "tco_code", nullable = false)
    private String code;

    @Column(name = "tco_value", nullable = false)
    private BigDecimal value;

    @Column(name = "tco_used", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean used;

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
