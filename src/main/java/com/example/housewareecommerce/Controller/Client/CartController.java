package com.example.housewareecommerce.Controller.Client;


import com.example.housewareecommerce.Model.DTO.CartDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CartRequest;
import com.example.housewareecommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SuppressWarnings("all")
@RequestMapping("/api")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping(value = "/cart/{id}")
    public ResponseEntity<?> getCart(@PathVariable Long id){
        CartDTO cartDTO = cartService.getCartByUser(id);
        MessageDTO messageDTO = new MessageDTO();
        if (cartDTO == null){
            messageDTO.setMessage("Người dùng chưa có giỏ hàng");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }else {
            messageDTO.setMessage("Success");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(cartDTO);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest){
        MessageDTO messageDTO = cartService.addToCart(cartRequest);
        if (messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody CartRequest cartRequest){
        MessageDTO messageDTO = cartService.removeFromCart(cartRequest);

        if (messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST ||
                messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, messageDTO.getHttpStatus());
        }

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cart/delete")
    public ResponseEntity<?> deleteProductInCart(@RequestBody CartRequest cartRequest){
        MessageDTO messageDTO = cartService.deleteProductInCart(cartRequest);
        if (messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getCartItemCount(@PathVariable Long userId) {
        Long count = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(count);
    }
}
