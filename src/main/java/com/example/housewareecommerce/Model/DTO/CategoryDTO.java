package com.example.housewareecommerce.Model.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Base64;

public class CategoryDTO {
    private Long id;
    private String nameCategory;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime created;
    
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public String getBase64Image() {
        if (image != null) {
            return Base64.getEncoder().encodeToString(image);
        }
        return null;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String nameCategory, String description) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
