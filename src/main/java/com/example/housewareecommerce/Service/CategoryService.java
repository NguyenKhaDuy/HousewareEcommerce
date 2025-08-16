package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    List<CategoryDTO> getAll();
    MessageDTO getById(Long id);

    Optional<CategoryDTO> getByCategoryById(Long id);

    MessageDTO createCategory(CategoryRequest categoryRequest);
    MessageDTO updateCategory(CategoryRequest categoryRequest);
    MessageDTO deleteCategory(Long id);
}
