package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.FavoriteProductDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.FavoriteProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public interface FavoriteProductService {
    Page<FavoriteProductDTO> getAllByUser(Long userId, Integer pageNo);

    MessageDTO addToFavorite(FavoriteProductRequest favoriteProductRequest);

    MessageDTO deleteFavoriteProduct(FavoriteProductRequest favoriteProductRequest);
}
