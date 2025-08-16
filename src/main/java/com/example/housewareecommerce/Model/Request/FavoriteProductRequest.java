package com.example.housewareecommerce.Model.Request;

import java.time.LocalDate;

public class FavoriteProductRequest {
    private Long productId;
    private Long userId;
    private LocalDate created;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
