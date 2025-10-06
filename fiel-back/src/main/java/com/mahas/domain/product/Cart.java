package com.mahas.domain.product;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CartId.class)
public class Cart extends DomainEntity {
   
    @Id
    @ManyToOne
    @JoinColumn(name = "crt_usr_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
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


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CartId implements Serializable {
    private Integer user;
    private Integer book; 

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartId)) return false;
        CartId that = (CartId) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }
}