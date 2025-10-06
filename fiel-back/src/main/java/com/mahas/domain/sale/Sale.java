package com.mahas.domain.sale;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.address.Address;
import com.mahas.domain.user.User;

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
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sal_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sal_usr_id", nullable = false)
    private User user;

    @Column(name = "sal_freight")
    private Double freight;

    @Column(name = "sal_delivery_date")
    private LocalDate deliveryDate;

    @ManyToOne
    @JoinColumn(name = "sal_ssa_id", nullable = false)
    private StatusSale statusSale;

    @ManyToOne
    @JoinColumn(name = "sal_tco_id")
    private TraderCoupon traderCoupon;

    @ManyToOne
    @JoinColumn(name = "sal_pco_id")
    private PromotionalCoupon promotionalCoupon;

    @ManyToOne
    @JoinColumn(name = "sal_add_id", nullable = false)
    private Address address;

    @Column(name = "sal_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sal_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "sal_published_at", nullable = false)
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
