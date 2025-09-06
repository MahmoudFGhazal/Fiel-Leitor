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
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_id")
    private Long id;

    @Column(name = "add_name", nullable = false)
    private String name;

    @Column(name = "add_number", nullable = false)
    private String number;

    @Column(name = "add_complement", nullable = false)
    private String complement;

    @Column(name = "add_rty_id", nullable = false)
    private Long rtyId;

    @Column(name = "add_cit_id", nullable = false)
    private Long citId;

    @Column(name = "add_str_id", nullable = false)
    private Long strId;

    @Column(name = "add_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "add_updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "add_publishedAt", nullable = false)
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
