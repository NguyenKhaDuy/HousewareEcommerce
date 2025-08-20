package com.example.housewareecommerce.Model.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CategoryDTO {
    private Long id;
    private String nameCategory;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime created;

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
