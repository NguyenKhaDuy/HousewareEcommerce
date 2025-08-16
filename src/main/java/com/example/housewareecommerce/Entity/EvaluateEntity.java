package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "evaluate")
@SuppressWarnings("all")

public class EvaluateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "star")
    private Integer star;
    @Column(name = "note")
    private String note;
    @Column(name = "created")
    private LocalDate created;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "productid")
    private ProductEntity productEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}
