package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Entity.ImageEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import com.example.housewareecommerce.Service.CategoryService;
import com.example.housewareecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class ClientProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


    @GetMapping("/category/{categoryId}")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
        try {

            List<CategoryDTO> categories = categoryService.getAll();
            model.addAttribute("categories", categories);

            // Lấy thông tin category hiện tại
            CategoryDTO currentCategory = categoryService.getByCategoryById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Category id = " + categoryId));
            model.addAttribute("currentCategory", currentCategory);

            // Lấy sản phẩm theo category
            List<ProductDTO> products = productService.getProductsByCategoryId(categoryId);

            System.out.println("Category ID: " + categoryId);
            System.out.println("Products found: " + products.size());
            for (ProductDTO product : products) {
                System.out.println("Product: " + product.getNameProduct() +
                        ", Images: " + (product.getImagesBase64() != null ? product.getImagesBase64().size() : "null"));
                if (product.getImagesBase64() != null && !product.getImagesBase64().isEmpty()) {
                    System.out.println("  First image length: " + product.getImagesBase64().get(0).length());
                }
            }

            model.addAttribute("products", products);
            model.addAttribute("showProducts", "user/products-by-category");

            return "UserHome";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải sản phẩm cho danh mục này: " + e.getMessage());
            return "UserHome";
        }
    }

    @GetMapping("/product/{productId}")
    public String getProductDetail(@PathVariable Long productId, Model model) {
        try {

            ProductEntity product = productService.getProductDetailById(productId);

            model.addAttribute("product", product);

            // Debug log
            System.out.println("Product ID: " + productId);
            System.out.println("Product Images size: " + product.getImageEntities().size());


            return "ProductDetails";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Không thể tải chi tiết sản phẩm: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/back-to-categories")
    public String backToCategories(Model model) {
        return "redirect:/";
    }
}
