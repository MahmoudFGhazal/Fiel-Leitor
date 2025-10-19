package com.mahas.domain.sale;

import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "status_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusSale extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssa_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ssa_status", nullable = false, unique = true)
    private StatusSaleName status;

    @Column(name = "ssa_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "ssa_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "ssa_published_at", nullable = false)
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
