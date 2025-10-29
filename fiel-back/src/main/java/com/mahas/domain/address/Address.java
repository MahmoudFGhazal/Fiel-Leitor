package com.mahas.domain.address;

import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.User;

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
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "add_usr_id", nullable = false)
    private User user;

    @Column(name = "add_principal", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean principal = false;

    @Column(name = "add_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean active = true;
    
    @Column(name = "add_nickname", nullable = false)
    private String nickname;

    @Column(name = "add_number", nullable = false)
    private String number;

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

    @Column(name = "add_is_delete", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "add_sty_id", nullable = false)
    private StreetType streetType;

    @ManyToOne
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
        if (isDelete == null) isDelete = false; 
        createdAt = updatedAt = publishedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        publishedAt = LocalDateTime.now();
    }
}
