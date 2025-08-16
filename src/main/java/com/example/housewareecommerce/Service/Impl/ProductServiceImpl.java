package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.*;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import com.example.housewareecommerce.Model.Request.ProductRequest;
import com.example.housewareecommerce.Repository.CategoryRepository;
import com.example.housewareecommerce.Repository.EvaluateRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@SuppressWarnings("all")
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EvaluateRepository evaluateRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<ProductEntity> products = productRepository.findByCategoryIdWithImages(categoryId);

        if (products.isEmpty()) {
            return new ArrayList<>();
        }

        return products.stream()
                .map(this::convertToListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductEntity getProductDetailById(Long productId) {
        ProductEntity product = productRepository.findByIdWithBasicRelations(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));

        ProductEntity productWithImages = productRepository.findByIdWithImages(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy product Images = " + productId));
        product.setImageEntities(productWithImages.getImageEntities());

        ProductEntity productWithEvaluates = productRepository.findByIdWithEvaluates(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy evalute = " + productId));
        product.setEvaluateEntities(productWithEvaluates.getEvaluateEntities());

        if (product.getImageEntities() != null) {
            product.getImageEntities().forEach(img -> {
                if (img.getImageUrl() != null && img.getImageUrl().length > 0) {
                    String encodedImage = Base64.getEncoder().encodeToString(img.getImageUrl());
                    img.setBase64Image(encodedImage);
                }
            });
        }

        return product;
    }

    private ProductDTO convertToListDTO(ProductEntity product) {
        List<String> imageList = new ArrayList<>();

        if (product.getImageEntities() != null && !product.getImageEntities().isEmpty()) {
            imageList = product.getImageEntities()
                    .stream()
                    .filter(img -> img.getImageUrl() != null && img.getImageUrl().length > 0)
                    .map(img -> Base64.getEncoder().encodeToString(img.getImageUrl()))
                    .collect(Collectors.toList());
        }

        return new ProductDTO(
                product.getId(),
                product.getNameProduct(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                imageList,
                product.getStatusEntity().getStatusCode()
        );
    }

    @Override
    public Page<ProductDTO> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        List<ProductDTO> results = new ArrayList<>();
        for(ProductEntity productEntity : productEntities){
            ProductDTO productDTO = new ProductDTO();
            modelMapper.map(productEntity, productDTO);
            productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
            productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
            List<byte[]> images = new ArrayList<>();
            for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                if (imageBytes != null && imageBytes.length > 0) {
                    images.add(imageBytes);
                }
            }
            productDTO.setImages(images);
            results.add(productDTO);
        }
        return new PageImpl<>(results, productEntities.getPageable(), productEntities.getTotalElements());
    }

    @Override
    public MessageDTO findById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            ProductEntity productEntity = productRepository.findById(id).get();
            Integer evaluateRating = averageRating(id);
            ProductDTO productDTO = new ProductDTO();
            modelMapper.map(productEntity, productDTO);
            productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
            productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
            List<byte[]> images = new ArrayList<>();
            for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                if (imageBytes != null && imageBytes.length > 0) {
                    images.add(imageBytes);
                }
            }
            //productDTO.setEvaluateRating(evaluateRating);
            productDTO.setImages(images);
            messageDTO.setMessage("Sản phẩm tồn tại");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(productDTO);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    public Integer averageRating(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).get();
        List<EvaluateEntity> evaluateEntities = evaluateRepository.findByProductEntity(productEntity);
        Integer sumStar = 0;
        for (EvaluateEntity it : evaluateEntities){
            sumStar += it.getStar();
        }
        Integer result = sumStar / evaluateEntities.size();
        return result;
    }

    @Override
    public MessageDTO createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = new ProductEntity();
        MessageDTO messageDTO = new MessageDTO();
        modelMapper.map(productRequest, productEntity);
        try{
            StatusEntity statusEntity  = statusRepository.findById(productRequest.getStatusCode()).get();
            CategoryEntity categoryEntity = categoryRepository.findById(productRequest.getCategoryCode()).get();
            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setStatusEntity(statusEntity);
            productEntity.setCreated(LocalDateTime.now());
            if (productRequest.getImages() != null && !productRequest.getImages().isEmpty()) {
                for (MultipartFile file : productRequest.getImages()) {
                    try {
                        ImageEntity imageEntity = new ImageEntity();
                        imageEntity.setImageUrl(file.getBytes());
                        imageEntity.setProductEntity(productEntity);
                        productEntity.getImageEntities().add(imageEntity);
                    } catch (IOException e) {
                        messageDTO.setMessage("Lỗi xử lý ảnh");
                        messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                        messageDTO.setData(null);
                        return messageDTO;
                    }
                }
            }
            ProductEntity check = productRepository.save(productEntity);
            if (check == null) {
                messageDTO.setMessage("Thêm sản phảm không thành công");
                messageDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                messageDTO.setData(null);
            }else{
                ProductDTO productDTO = new ProductDTO();
                modelMapper.map(productEntity, productDTO);
                productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
                productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
                List<byte[]> images = new ArrayList<>();
                for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                    byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                    if (imageBytes != null && imageBytes.length > 0) {
                        images.add(imageBytes);
                    }
                }
                productDTO.setImages(images);;
                messageDTO.setMessage("Tạo sản phẩm thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(productDTO);
            }
        }catch (Exception e){
            messageDTO.setMessage("Thêm sản phảm không thành công");
            messageDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messageDTO.setData(null);
        }

        return messageDTO;
    }

    @Override
    public MessageDTO updateProduct(ProductRequest productRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            ProductEntity productEntity = productRepository.findById(productRequest.getId()).get();

            modelMapper.map(productRequest, productEntity);
            StatusEntity statusEntity  = statusRepository.findById(productRequest.getStatusCode()).get();
            CategoryEntity categoryEntity = categoryRepository.findById(productRequest.getCategoryCode()).get();
            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setStatusEntity(statusEntity);
            try{
                ProductEntity product = productRepository.save(productEntity);

                ProductDTO productDTO = new ProductDTO();
                modelMapper.map(product, productDTO);
                productDTO.setCategoryName(product.getCategoryEntity().getNameCategory());
                productDTO.setStatusCode(product.getStatusEntity().getStatusCode());
                List<byte[]> images = new ArrayList<>();
                for (ImageEntity imageEntity : product.getImageEntities()) {
                    byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                    if (imageBytes != null && imageBytes.length > 0) {
                        images.add(imageBytes);
                    }
                }
                productDTO.setImages(images);
                messageDTO.setMessage("Cập nhật thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(productDTO);
            }catch (Exception e){
                messageDTO.setMessage("Cập nhật không thành công");
                messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                messageDTO.setData(null);
            }

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteProduct(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            ProductEntity productEntity = productRepository.findById(id).get();
            try{
                productRepository.deleteById(id);
                messageDTO.setMessage("Xóa thành công sản phẩm");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(null);
            }catch (Exception e){
                messageDTO.setMessage("Xóa không thành công");
                messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                messageDTO.setData(null);
            }

        }catch(NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public Page<ProductDTO> searchProduct(Integer pageNo, String nameProduct) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Page<ProductEntity> productEntities = productRepository.searchProduct(pageable, nameProduct);

        List<ProductDTO> results = new ArrayList<>();
        for(ProductEntity productEntity : productEntities){
            ProductDTO productDTO = new ProductDTO();
            modelMapper.map(productEntity, productDTO);
            productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
            productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
            List<byte[]> images = new ArrayList<>();
            for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                if (imageBytes != null && imageBytes.length > 0) {
                    images.add(imageBytes);
                }
            }
            productDTO.setImages(images);
            results.add(productDTO);
        }

        return new PageImpl<>(results, productEntities.getPageable(), productEntities.getTotalElements());
    }
}
