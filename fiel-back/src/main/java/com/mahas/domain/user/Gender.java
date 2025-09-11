package com.mahas.domain.user;

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

@Entity(name = "Gender")
@Table(name = "genders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gender extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id")
    private Integer id;

    @Column(name = "gen_gender", nullable = false)
    private String gender;

    @Column(name = "gen_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "gen_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "gen_published_at", nullable = false)
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
