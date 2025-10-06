package com.mahas.domain.sale;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.Card;

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
@Table(name = "sales_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleCard extends DomainEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "sca_sal_id", nullable = false)
    private Sale sale;

    @Id
    @ManyToOne
    @JoinColumn(name = "sca_car_id", nullable = false)
    private Card card;

    @Column(name = "sca_percent", nullable = false)
    private Double percent;

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


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class SalesCardId implements Serializable {
    private Integer sale;
    private Integer card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesCardId)) return false;
        SalesCardId that = (SalesCardId) o;
        return Objects.equals(sale, that.sale) && Objects.equals(card, that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, card);
    }
}
