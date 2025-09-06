package com.mahas.domain.address;

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
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cit_id")
    private Long id;

    @Column(name = "cit_city", nullable = false)
    private String city;

    @Column(name = "cit_sta_id", nullable = false)
    private String staId;

    @Column(name = "cit_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "cit_updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "cit_publishedAt", nullable = false)
    private LocalDateTime publishedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = publishedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        publishedAt = LocalDateTime.now();
    }
}
