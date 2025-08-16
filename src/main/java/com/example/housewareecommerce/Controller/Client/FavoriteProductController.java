package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Model.DTO.FavoriteProductDTO;
import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.FavoriteProductRequest;
import com.example.housewareecommerce.Service.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SuppressWarnings("all")
public class FavoriteProductController {
    @Autowired
    FavoriteProductService favoriteProductService;

    @GetMapping(value = "/favorite/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long id, @RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        Page<FavoriteProductDTO> favoriteProductDTOS = favoriteProductService.getAllByUser(id, pageNo);
        ListDTO<FavoriteProductDTO> listDTO = new ListDTO<>();
        listDTO.setCurrentPage(pageNo);
        listDTO.setTotalPage(favoriteProductDTOS.getTotalPages());
        listDTO.setData(favoriteProductDTOS.getContent());

        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/favorite")
    public ResponseEntity<?> addToFavorite(@RequestBody FavoriteProductRequest favoriteProductRequest){
        MessageDTO messageDTO = favoriteProductService.addToFavorite(favoriteProductRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorite")
    public ResponseEntity<?> deleteFavoriteProduct(@RequestBody FavoriteProductRequest favoriteProductRequest){
        MessageDTO messageDTO = favoriteProductService.deleteFavoriteProduct(favoriteProductRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }else if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
