package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.CartEntity;
import com.example.housewareecommerce.Entity.CartItemEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.CartDTO;
import com.example.housewareecommerce.Model.DTO.CartItemDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CartRequest;
import com.example.housewareecommerce.Repository.CartItemRepository;
import com.example.housewareecommerce.Repository.CartRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Repository.UserRepository;
import com.example.housewareecommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@SuppressWarnings("all")
public class CartServiceImpl implements CartService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;
    @Override
    public CartDTO getCartByUser(Long userId) {
        CartDTO cartDTO = new CartDTO();
        try{
            UserEntity userEntity = userRepository.findById(userId).get();
            if(userEntity.getCartEntity() != null){
                CartEntity cartEntity = userEntity.getCartEntity();
                cartDTO.setId(cartEntity.getId());
                cartDTO.setUserId(userId);
                cartDTO.setUserName(userEntity.getName());
                for(CartItemEntity it : cartEntity.getCartItemEntities()){
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setId(it.getId());
                    cartItemDTO.setQuantity(it.getQuantity());
                    cartItemDTO.setSubTotal(it.getSubTotal());
                    cartItemDTO.setProductId(it.getProductEntity().getId());
                    cartItemDTO.setProductName(it.getProductEntity().getNameProduct());
                    cartItemDTO.setImageProduct(it.getProductEntity().getImageEntities().getFirst().getImageUrl());
                    cartDTO.getCartItemDTOS().add(cartItemDTO);
                }
            }else {
                return null;
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return cartDTO;
    }

    @Override
    public MessageDTO addToCart(CartRequest cartRequest) {
        MessageDTO messageDTO = new MessageDTO();
        CartEntity cartEntity = null;
        try {
            UserEntity userEntity = userRepository.findById(cartRequest.getUserId()).get();
            //Kiểm tra xem user đã có giỏ hàng hay chưa
            if(userEntity.getCartEntity() == null){
                CartEntity cart = new CartEntity();
                cart.setUserEntity(userEntity);
                cartEntity = cartRepository.save(cart);
            }else{
                //nếu có rồi sẽ lấy giỏ hàng ra thông qua user
                cartEntity = cartRepository.findByUserEntity(userEntity);
            }
            //Tìm kiếm sản phẩm thông qua id sản phẩm
            ProductEntity productEntity = productRepository.findById(cartRequest.getProductId()).get();
            //Tìm kiếm sản phẩm trong giỏ hàng thông qua product và cart
            CartItemEntity cartItemEntity = cartItemRepository.findByProductEntityAndAndCartEntity(productEntity,cartEntity);
            //Kiểm tra xem giỏ hàng đã có product này hay chưa
            if(cartEntity.getCartItemEntities().contains(cartItemEntity)){
                //nếu có rồi thì tiến hành cộng số lượng vừa mới thêm vào và số lượng có sẵn trong giỏ hàng
                Long quality = cartItemEntity.getQuantity() + cartRequest.getQuantity();
                //kiểm tra số lượng có vượt quá số lượng sản phẩm tồn kho không
                if(productEntity.getQuantity() < quality){
                    messageDTO.setMessage("Số lượng vượt quá số lượng trong kho");
                    messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                    messageDTO.setData(null);
                    return messageDTO;
                }else{
                    //nếu không vượt thì thêm số lượng mới vào cartItem
                    cartItemEntity.setQuantity(quality);
                    cartItemEntity.setSubTotal(cartItemEntity.getSubTotal() + cartRequest.getSubTotal());
                    cartItemRepository.save(cartItemEntity);
                }
            }else {
                //nếu product chưa có trong cartitem thì tiến hành thêm mới
                CartItemEntity cartItem = new CartItemEntity();
                cartItem.setProductEntity(productEntity);
                cartItem.setQuantity(cartRequest.getQuantity());
                cartItem.setSubTotal(cartRequest.getSubTotal());
                cartItem.setCartEntity(cartEntity);
                cartItemRepository.save(cartItem);
            }

            messageDTO.setMessage("Thêm vào giỏ hàng thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy user");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteProductInCart(CartRequest cartRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            UserEntity userEntity = userRepository.findById(cartRequest.getUserId()).get();
            if(userEntity.getCartEntity() == null){
                messageDTO.setMessage("User chưa có giỏ hàng");
                messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
                messageDTO.setData(null);
                return messageDTO;
            }
            ProductEntity productEntity = productRepository.findById(cartRequest.getProductId()).get();
            CartEntity cartEntity = userEntity.getCartEntity();
            CartItemEntity cartItemEntity = cartItemRepository.findByProductEntityAndAndCartEntity(productEntity, cartEntity);
            if (cartEntity.getCartItemEntities().contains(cartItemEntity)){
                cartEntity.getCartItemEntities().remove(cartItemEntity);
                cartRepository.save(cartEntity);

                messageDTO.setMessage("Xóa thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(null);
            }
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Fails");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}
