package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.ImageEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Model.DTO.*;
import com.example.housewareecommerce.Model.Request.ImageRequest;
import com.example.housewareecommerce.Repository.ImageProductRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ImageProductServiceImpl implements ImageProductService {
    @Autowired
    ImageProductRepository imageProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<ImageProductDTO> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Page<ImageEntity> imageEntities = imageProductRepository.findAll(pageable);

        List<ImageProductDTO> results = new ArrayList<>();

        for(ImageEntity imageEntity : imageEntities){
            ImageProductDTO imageProductDTO = new ImageProductDTO();
            imageProductDTO.setId(imageEntity.getId());
            imageProductDTO.setProductId(imageEntity.getProductEntity().getId());
            imageProductDTO.setNameProduct(imageEntity.getProductEntity().getNameProduct());
            imageProductDTO.setImage(imageEntity.getImageUrl());
            results.add(imageProductDTO);
        }


        return new PageImpl<>(results, pageable, imageEntities.getTotalElements());
    }


    @Override
    public MessageDTO getById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            ProductEntity productEntity = productRepository.findById(id).get();

            List<ImageEntity> imageEntities = imageProductRepository.findByProductEntity(productEntity);
            List<ImageDTO> imageDTOS = new ArrayList<>();
            ImageProductByIdDTO imageProductByIdDTO = new ImageProductByIdDTO();
            imageProductByIdDTO.setProductId(productEntity.getId());
            imageProductByIdDTO.setNameProduct(productEntity.getNameProduct());

            for(ImageEntity imageEntity: imageEntities){
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(imageEntity.getId());
                imageDTO.setImage(imageEntity.getImageUrl());
                imageDTOS.add(imageDTO);
            }

            imageProductByIdDTO.setImages(imageDTOS);

            messageDTO.setMessage("Success");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(imageProductByIdDTO);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO createImage(ImageRequest imageRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            ProductEntity productEntity = productRepository.findById(imageRequest.getProductId()).get();

            if (imageRequest.getImages() != null && !imageRequest.getImages().isEmpty()) {
                for (MultipartFile file : imageRequest.getImages()) {
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

            productRepository.save(productEntity);

            messageDTO.setMessage("Thêm ảnh thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteImage(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            Optional<ImageEntity> optionalImage = imageProductRepository.findById(id);
            if (optionalImage.isPresent()) {
                imageProductRepository.deleteById(id);
            }

            messageDTO.setMessage("Xóa thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO updateImage(Long imageId, MultipartFile newImage) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            Optional<ImageEntity> optionalImage = imageProductRepository.findById(imageId);
            if (optionalImage.isPresent()) {
                ImageEntity imageEntity = optionalImage.get();
                imageEntity.setImageUrl(newImage.getBytes());
                imageProductRepository.save(imageEntity);

                messageDTO.setMessage("Cập nhật ảnh thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(null);
            } else {
                messageDTO.setMessage("Không tìm thấy ảnh");
                messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
                messageDTO.setData(null);
            }
        } catch (IOException e) {
            messageDTO.setMessage("Lỗi xử lý ảnh");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}
