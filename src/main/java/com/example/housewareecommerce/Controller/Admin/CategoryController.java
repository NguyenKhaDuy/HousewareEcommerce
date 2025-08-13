package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CategoryRequest;
import com.example.housewareecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/admin/category")
    public ResponseEntity<?> getAll(){
        List<CategoryDTO> categoryDTOS = categoryService.getAll();
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/category/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        MessageDTO messageDTO = categoryService.getById(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest){
        MessageDTO messageDTO = categoryService.createCategory(categoryRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/admin/category")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest){
        MessageDTO messageDTO = categoryService.updateCategory(categoryRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        MessageDTO messageDTO = categoryService.deleteCategory(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
