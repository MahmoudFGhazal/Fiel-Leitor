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
@Table(name = "address_names")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressName extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ana_id")
    private Long id;

    @Column(name = "ana_name", nullable = false)
    private String name;

    @Column(name = "ana_add_id", nullable = false)
    private Long addId;

    @Column(name = "ana_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "ana_updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "ana_publishedAt", nullable = false)
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
