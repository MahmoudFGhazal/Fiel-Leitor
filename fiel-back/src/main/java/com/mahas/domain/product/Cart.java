package com.mahas.domain.product;

import java.time.LocalDateTime;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends DomainEntity {
   
    @EmbeddedId
    private CartId id;

    @MapsId("userId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "crt_usr_id", nullable = false)
    private User user;

    @MapsId("bookId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "crt_bok_id", nullable = false)
    private Book book;

    @Column(name = "crt_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "crt_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "crt_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "crt_published_at", nullable = false)
    private LocalDateTime publishedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = publishedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = publishedAt = LocalDateTime.now();
    }
}
