package com.mahas.domain.sale;

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
@Table(name = "satus_sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusSale extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssa_id")
    private Integer id;

    @Column(name = "ssa_name", nullable = false)
    private String name;

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
