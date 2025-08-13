package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.ImageEntity;
import com.example.housewareecommerce.Model.DTO.ImageProductDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Repository.ImageProductRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            imageProductDTO.setNameProduct(imageEntity.getProductEntity().getNameProduct());
            imageProductDTO.getImages().add(imageEntity.getImageUrl());
            results.add(imageProductDTO);
        }
        return new PageImpl<>(results, imageEntities.getPageable(), imageEntities.getTotalElements());
    }

    @Override
    public MessageDTO getById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        return null;
    }
}
