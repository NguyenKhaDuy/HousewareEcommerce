package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "address")
    private String address;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name = "gender")
    private String gender;
    @Column(name = "created")
    private Date created;
    @Column(name = "role")
    private Long role;

    @ManyToOne
    @JoinColumn(name = "statusid")
    private StatusEntity statusEntity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<FavoriteProductEntity> favoriteProductEntities = new ArrayList<>();

    @OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.PERSIST},orphanRemoval = true)
    private CartEntity cartEntity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<EvaluateEntity> evaluateEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<OrderEntity> orderEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

    public List<FavoriteProductEntity> getFavoriteProductEntities() {
        return favoriteProductEntities;
    }

    public void setFavoriteProductEntities(List<FavoriteProductEntity> favoriteProductEntities) {
        this.favoriteProductEntities = favoriteProductEntities;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
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

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}
