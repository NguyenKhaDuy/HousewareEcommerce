package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.FavoriteProductEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.FavoriteProductDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.FavoriteProductRequest;
import com.example.housewareecommerce.Repository.FavoriteProductRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Repository.UserRepository;
import com.example.housewareecommerce.Service.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@SuppressWarnings("all")
public class FavoriteProductServiceImpl implements FavoriteProductService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FavoriteProductRepository favoriteProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public Page<FavoriteProductDTO> getAllByUser(Long userId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        List<FavoriteProductDTO> favoriteProductDTOS = new ArrayList<>();
        Page<FavoriteProductEntity> favoriteProductEntities = null;
        try {
            UserEntity userEntity = userRepository.findById(userId).get();
            favoriteProductEntities = favoriteProductRepository.findByUserEntity(userEntity, pageable);
            for (FavoriteProductEntity it : favoriteProductEntities.getContent()) {
                FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO();
                favoriteProductDTO.setId(it.getId());
                favoriteProductDTO.setProductId(it.getProductEntity().getId());
                favoriteProductDTO.setProductName(it.getProductEntity().getNameProduct());
                favoriteProductDTO.setCreated(it.getCreated());
                favoriteProductDTO.setImageProduct(it.getProductEntity().getImageEntities().getFirst().getImageUrl());
                favoriteProductDTOS.add(favoriteProductDTO);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return new PageImpl<>(favoriteProductDTOS, favoriteProductEntities.getPageable(), favoriteProductEntities.getTotalElements());
    }

    @Override
    public MessageDTO addToFavorite(FavoriteProductRequest favoriteProductRequest) {
        MessageDTO messageDTO = new MessageDTO();
        UserEntity userEntity = null;
        ProductEntity productEntity = null;
        try{
            userEntity = userRepository.findById(favoriteProductRequest.getUserId()).get();
            productEntity = productRepository.findById(favoriteProductRequest.getProductId()).get();
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Thêm không thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
            return messageDTO;
        }
        FavoriteProductEntity favoriteProductEntity = new FavoriteProductEntity();
        favoriteProductEntity.setUserEntity(userEntity);
        favoriteProductEntity.setProductEntity(productEntity);
        favoriteProductEntity.setCreated(LocalDate.now());
        favoriteProductRepository.save(favoriteProductEntity);

        messageDTO.setMessage("Thêm thành công");
        messageDTO.setHttpStatus(HttpStatus.OK);
        messageDTO.setData(null);

        return messageDTO;
    }

    @Override
    public MessageDTO deleteFavoriteProduct(FavoriteProductRequest favoriteProductRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            UserEntity userEntity = userRepository.findById(favoriteProductRequest.getUserId()).get();
            ProductEntity productEntity = productRepository.findById(favoriteProductRequest.getProductId()).get();
            FavoriteProductEntity favoriteProductEntity = favoriteProductRepository.findByUserEntityAndProductEntity(userEntity, productEntity);
            if(favoriteProductEntity != null){
                favoriteProductRepository.deleteById(favoriteProductEntity.getId());
                messageDTO.setMessage("Xóa thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(null);
            }else {
                messageDTO.setMessage("Sản phẩm yêu thích không tồn tại");
                messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
                messageDTO.setData(null);
            }
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Xóa không thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}
