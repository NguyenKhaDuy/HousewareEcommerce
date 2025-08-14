package com.example.housewareecommerce.Model.Request;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ImageRequest {
    private Long productId;
    private List<MultipartFile> images = new ArrayList<>();

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
