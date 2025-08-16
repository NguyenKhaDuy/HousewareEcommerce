package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.CategoryEntity;
import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CategoryRequest;
import com.example.housewareecommerce.Repository.CategoryRepository;
import com.example.housewareecommerce.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryDTO> results = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities){
            CategoryDTO categoryDTO = new CategoryDTO();
            modelMapper.map(categoryEntity, categoryDTO);
            results.add(categoryDTO);
        }
        return results;
    }

    @Override
    public MessageDTO getById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(id).get();
            CategoryDTO categoryDTO = new CategoryDTO();
            modelMapper.map(categoryEntity, categoryDTO);

            messageDTO.setMessage("Success");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(categoryDTO);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Danh mục không tồn tại");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public Optional<CategoryDTO> getByCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryEntity -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(categoryEntity.getId());
                    dto.setNameCategory(categoryEntity.getNameCategory());
                    dto.setDescription(categoryEntity.getDescription());
                    dto.setCreated(categoryEntity.getCreated());
                    return dto;
                });
    }

    @Override
    public MessageDTO createCategory(CategoryRequest categoryRequest) {
        MessageDTO messageDTO = new MessageDTO();

        try{
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setNameCategory(categoryRequest.getNameCategory());
            categoryEntity.setDescription(categoryRequest.getDescription());
            categoryEntity.setCreated(LocalDateTime.now());
            CategoryEntity check = categoryRepository.save(categoryEntity);

            CategoryDTO categoryDTO = new CategoryDTO();
            modelMapper.map(check, categoryDTO);

            messageDTO.setMessage("Thêm thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(categoryDTO);

        }catch (Exception e){
            messageDTO.setMessage("Thêm không thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
        }

        return messageDTO;
    }

    @Override
    public MessageDTO updateCategory(CategoryRequest categoryRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(categoryRequest.getId()).get();

            categoryEntity.setNameCategory(categoryRequest.getNameCategory());
            categoryEntity.setDescription(categoryRequest.getDescription());

            CategoryEntity category = categoryRepository.save(categoryEntity);
            CategoryDTO categoryDTO = new CategoryDTO();
            modelMapper.map(category, categoryDTO);

            messageDTO.setMessage("Cập nhật thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(categoryDTO);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy danh mục");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteCategory(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(id).get();
            categoryRepository.deleteById(id);
            messageDTO.setMessage("Xóa thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy danh mục");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}
