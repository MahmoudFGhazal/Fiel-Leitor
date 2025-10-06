package com.mahas.domain.user;

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
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "car_usr_id", nullable = false)
    private User user;

    @Column(name = "car_principal", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean principal = false;

    @Column(name = "car_bin", nullable = false, length = 20)
    private String bin;

    @Column(name = "car_last4", length = 4)
    private String last4;

    @Column(name = "car_holder", nullable = false)
    private String holder;

    @Column(name = "car_exp_month", length = 2)
    private String expMonth;

    @Column(name = "car_exp_year", length = 4)
    private String expYear;

    @Column(name = "car_brand", nullable = false, length = 8)
    private String brand;

    @Column(name = "car_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "car_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "car_published_at", nullable = false)
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
