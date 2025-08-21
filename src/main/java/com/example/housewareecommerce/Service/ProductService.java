package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import com.example.housewareecommerce.Model.Request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Page<ProductDTO> getAll(Integer pageNo);
    MessageDTO findById(Long id);
    MessageDTO createProduct(ProductRequest productRequest);
    MessageDTO updateProduct(ProductRequest productRequest);
    MessageDTO deleteProduct(Long id);

    Page<ProductDTO> searchProduct(Integer pageNo, String nameProduct);

    List<ProductDTO> getProductsByCategoryId(Long categoryId);

    ProductEntity getProductDetailById(Long productId);

    public List<ProductDTO> getAll();
    
}
