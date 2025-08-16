package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.CommentEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.CommentDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CommentRequest;
import com.example.housewareecommerce.Repository.CommentRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Repository.UserRepository;
import com.example.housewareecommerce.Service.CommentService;
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
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Page<CommentDTO> getAllByProduct(Long productId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        ProductEntity productEntity = productRepository.findById(productId).get();
        Page<CommentEntity> commentEntities = commentRepository.findByProductEntity(productEntity, pageable);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (CommentEntity it : commentEntities){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(it.getId());
            commentDTO.setCreated(it.getCreated());
            commentDTO.setUserId(it.getUserEntity().getId());
            commentDTO.setUserName(it.getUserEntity().getName());
            commentDTO.setContent(it.getContent());
            commentDTOS.add(commentDTO);
        }
        return new PageImpl<>(commentDTOS, commentEntities.getPageable(), commentEntities.getTotalElements());
    }

    @Override
    public MessageDTO createComment(CommentRequest commentRequest) {
        MessageDTO messageDTO = new MessageDTO();
        ProductEntity productEntity = null;
        UserEntity userEntity = null;
        try{
            productEntity = productRepository.findById(commentRequest.getProductId()).get();
            userEntity = userRepository.findById(commentRequest.getUserId()).get();
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Comment không thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
            return messageDTO;
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setCreated(LocalDate.now());
        commentEntity.setProductEntity(productEntity);
        commentEntity.setUserEntity(userEntity);
        commentRepository.save(commentEntity);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCreated(commentEntity.getCreated());
        commentDTO.setUserId(commentEntity.getUserEntity().getId());
        commentDTO.setUserName(commentEntity.getUserEntity().getName());
        commentDTO.setContent(commentEntity.getContent());

        messageDTO.setMessage("Comment thành công");
        messageDTO.setHttpStatus(HttpStatus.OK);
        messageDTO.setData(commentDTO);

        return messageDTO;
    }

    @Override
    public MessageDTO deleteComment(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            CommentEntity commentEntity = commentRepository.findById(id).get();
            commentRepository.deleteById(id);
            messageDTO.setMessage("Xóa thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy comment");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}
