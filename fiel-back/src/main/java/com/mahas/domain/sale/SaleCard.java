package com.mahas.domain.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.Card;

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
@Table(name = "sales_cards")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class SaleCard extends DomainEntity {

    @EmbeddedId
    private SaleCardId id;

    @MapsId("saleId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "sca_sal_id", referencedColumnName = "sal_id", nullable = false)
    private Sale sale;

    @MapsId("cardId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "sca_car_id", referencedColumnName = "car_id", nullable = false)
    private Card card;

    @Column(name = "sca_percent", nullable = false, precision = 5, scale = 2)
    private BigDecimal percent;

    @Column(name = "sca_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sca_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "sca_published_at", nullable = false)
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
