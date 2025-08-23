package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CategoryRequest;
import com.example.housewareecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ViewCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categoryManagementPage(Model model) {
        model.addAttribute("content", "admin/categoryManagement");
        return "/admin/AdminHome";
    }

    // Get all categories
    @GetMapping("/categories/all")
    @ResponseBody
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<CategoryDTO> categories = categoryService.getAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get category by ID
    @GetMapping("/category/{id}")
    @ResponseBody
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            MessageDTO messageDTO = categoryService.getById(id);
            return new ResponseEntity<>(messageDTO, messageDTO.getHttpStatus());
        } catch (Exception e) {
            MessageDTO errorMessage = new MessageDTO();
            errorMessage.setMessage("Có lỗi xảy ra khi lấy thông tin danh mục: " + e.getMessage());
            errorMessage.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorMessage.setData(null);
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create category
    @PostMapping(value = "/category")
    @ResponseBody
    public ResponseEntity<?> createCategory(@ModelAttribute CategoryRequest categoryRequest) {
        try {
            MessageDTO messageDTO = categoryService.createCategory(categoryRequest);

            if (messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST) {
                return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(messageDTO, HttpStatus.OK);

        } catch (Exception e) {
            MessageDTO errorMessage = new MessageDTO();
            errorMessage.setMessage("Có lỗi xảy ra khi thêm danh mục: " + e.getMessage());
            errorMessage.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorMessage.setData(null);

            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update category
    @PutMapping(value = "/category")
    @ResponseBody
    public ResponseEntity<?> updateCategory(@ModelAttribute CategoryRequest categoryRequest) {
        try {
            if (categoryRequest.getId() == null) {
                MessageDTO errorMessage = new MessageDTO();
                errorMessage.setMessage("ID danh mục không được để trống");
                errorMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
                errorMessage.setData(null);
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            MessageDTO messageDTO = categoryService.updateCategory(categoryRequest);
            return new ResponseEntity<>(messageDTO, messageDTO.getHttpStatus());

        } catch (Exception e) {
            MessageDTO errorMessage = new MessageDTO();
            errorMessage.setMessage("Có lỗi xảy ra khi cập nhật danh mục: " + e.getMessage());
            errorMessage.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorMessage.setData(null);

            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete category
    @DeleteMapping(value = "/category/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            MessageDTO messageDTO = categoryService.deleteCategory(id);
            return new ResponseEntity<>(messageDTO, messageDTO.getHttpStatus());

        } catch (Exception e) {
            MessageDTO errorMessage = new MessageDTO();
            errorMessage.setMessage("Có lỗi xảy ra khi xóa danh mục: " + e.getMessage());
            errorMessage.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorMessage.setData(null);

            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}