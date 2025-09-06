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
@Table(name = "streets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Street extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "str_id")
    private Long id;

    @Column(name = "str_street", nullable = false)
    private String street;

    @Column(name = "str_neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "str_zip", nullable = false)
    private String zip;

    @Column(name = "str_sty_id", nullable = false)
    private Long styId;

    @Column(name = "str_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "str_updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "str_publishedAt", nullable = false)
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
