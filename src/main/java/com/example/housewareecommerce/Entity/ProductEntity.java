package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameproduct")
    private String nameProduct;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Float price;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "importprice")
    private Float importPrice;
    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "statusid")
    private StatusEntity statusEntity;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ImageEntity> imageEntities = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<FavoriteProductEntity> favoriteProductEntities = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<CartItemEntity> cartItemEntities = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<EvaluateEntity> evaluateEntities = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<OrderDetailsEntity> orderDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Float importPrice) {
        this.importPrice = importPrice;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public List<ImageEntity> getImageEntities() {
        return imageEntities;
    }

    public void setImageEntities(List<ImageEntity> imageEntities) {
        this.imageEntities = imageEntities;
    }

    public List<FavoriteProductEntity> getFavoriteProductEntities() {
        return favoriteProductEntities;
    }

    public void setFavoriteProductEntities(List<FavoriteProductEntity> favoriteProductEntities) {
        this.favoriteProductEntities = favoriteProductEntities;
    }

    public List<CartItemEntity> getCartItemEntities() {
        return cartItemEntities;
    }

    public void setCartItemEntities(List<CartItemEntity> cartItemEntities) {
        this.cartItemEntities = cartItemEntities;
    }

    public List<EvaluateEntity> getEvaluateEntities() {
        return evaluateEntities;
    }

    public void setEvaluateEntities(List<EvaluateEntity> evaluateEntities) {
        this.evaluateEntities = evaluateEntities;
    }

    public List<CommentEntity> getCommentEntities() {
        return commentEntities;
    }

    public void setCommentEntities(List<CommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
    }

    public List<OrderDetailsEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Transient
    private Integer averageRating;

    public Integer getAverageRating() {
        if (evaluateEntities == null || evaluateEntities.isEmpty()) {
            return 0;
        }
        Integer sum = 0;
        for (EvaluateEntity e : evaluateEntities) {
            sum = sum + e.getStar();
        }
        return sum / evaluateEntities.size();
    }

}
