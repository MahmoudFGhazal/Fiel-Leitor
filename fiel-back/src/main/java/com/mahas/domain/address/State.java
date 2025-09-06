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
@Table(name = "states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sta_id")
    private Long id;

    @Column(name = "sta_state", nullable = false)
    private String state;

    @Column(name = "sta_uf", nullable = false)
    private String uf;

    @Column(name = "sta_cou_id", nullable = false)
    private Long couId;

    @Column(name = "sta_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sta_updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "sta_publishedAt", nullable = false)
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
