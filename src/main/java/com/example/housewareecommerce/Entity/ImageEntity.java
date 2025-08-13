package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "imageproduct")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "imageurl", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] imageUrl;

    @ManyToOne
    @JoinColumn(name = "productid")
    private ProductEntity productEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}
