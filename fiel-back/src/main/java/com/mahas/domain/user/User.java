package com.mahas.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;

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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_name", nullable = false)
    private String name;

    @Column(name = "usr_password", nullable = false)
    private String password;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "usr_gen_id", nullable = false)
    private Gender gender;

    @Column(name = "usr_birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "usr_cpf", nullable = false, length = 20)
    private String cpf;

    @Column(name = "usr_phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "usr_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "usr_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "usr_published_at", nullable = false)
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
