package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.CommentDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Page<CommentDTO> getAllByProduct(Long productId, Integer pageNo);
    MessageDTO createComment(CommentRequest commentRequest);
    MessageDTO deleteComment(Long id);
}
