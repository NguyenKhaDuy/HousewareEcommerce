package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "favoriteproduct")
public class FavoriteProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private Date created;

    @ManyToOne
    @JoinColumn(name = "productid")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
