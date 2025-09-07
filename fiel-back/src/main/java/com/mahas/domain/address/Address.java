package com.mahas.domain.address;

import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "add_usr_id", nullable = false)
    private User user;
    
    @Column(name = "add_nickname", nullable = false)
    private String nickname;

    @Column(name = "add_number", nullable = false)
    private Integer number;

    @Column(name = "add_complement")
    private String complement;

    @Column(name = "add_street", nullable = false)
    private String street;

    @Column(name = "add_neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "add_zip", nullable = false)
    private String zip;

    @Column(name = "add_city", nullable = false)
    private String city;

    @Column(name = "add_state", nullable = false)
    private String state;

    @Column(name = "add_country", nullable = false)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "add_sty_id", nullable = false)
    private StreetType streetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "add_rty_id", nullable = false)
    private ResidenceType residenceType;

    @Column(name = "add_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "add_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "add_published_at", nullable = false)
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
