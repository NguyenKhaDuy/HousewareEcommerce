package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.CategoryEntity;
import com.example.housewareecommerce.Entity.ImageEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import com.example.housewareecommerce.Model.Request.ProductRequest;
import com.example.housewareecommerce.Repository.CategoryRepository;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;
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
            List<String> images = new ArrayList<>();
            for(ImageEntity imageEntity : productEntity.getImageEntities()){
                String image = imageEntity.getImageUrl();
                images.add(image);
            }
            productDTO.setImages(images);
            results.add(productDTO);
        }
        return new PageImpl<>(results, productEntities.getPageable(), productEntities.getTotalElements());
    }

    @Override
    public MessageDTO findById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        ProductEntity productEntity = productRepository.findById(id).get();
        if(productEntity == null){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }else{
            ProductDTO productDTO = new ProductDTO();
            modelMapper.map(productEntity, productDTO);
            productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
            productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
            List<String> images = new ArrayList<>();
            for(ImageEntity imageEntity : productEntity.getImageEntities()){
                String image = imageEntity.getImageUrl();
                images.add(image);
            }
            productDTO.setImages(images);

            messageDTO.setMessage("Sản phẩm tồn tại");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(productDTO);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = new ProductEntity();
        MessageDTO messageDTO = new MessageDTO();
        modelMapper.map(productRequest, productEntity);
        StatusEntity statusEntity  = statusRepository.findById(productRequest.getStatusId()).get();
        CategoryEntity categoryEntity = categoryRepository.findById(productRequest.getCategoryId()).get();
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setStatusEntity(statusEntity);

        if (productRequest.getImages() != null && !productRequest.getImages().isEmpty()) {
            for (MultipartFile file : productRequest.getImages()) {
                try {
                    // Chuyển file sang Base64
                    String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setImageUrl(base64Image);
                    imageEntity.setProductEntity(productEntity);

                    productEntity.getImageEntities().add(imageEntity);
                } catch (IOException e) {
                    messageDTO.setMessage("Lỗi xử lí ảnh");
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
            List<String> images = new ArrayList<>();
            for(ImageEntity imageEntity : productEntity.getImageEntities()){
                String image = imageEntity.getImageUrl();
                images.add(image);
            }
            productDTO.setImages(images);

            messageDTO.setMessage("Tạo sản phẩm thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(productDTO);
        }



        return messageDTO;
    }
}
