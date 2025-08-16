package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Model.DTO.CommentDTO;
import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CommentRequest;
import com.example.housewareecommerce.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping(value = "/comment/{id}")
    public ResponseEntity<?> getAllByProduct(@PathVariable Long id, @RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        Page<CommentDTO> commentDTOS = commentService.getAllByProduct(id, pageNo);
        ListDTO<CommentDTO> listDTO = new ListDTO<>();
        listDTO.setCurrentPage(pageNo);
        listDTO.setTotalPage(commentDTOS.getTotalPages());
        listDTO.setData(commentDTOS.getContent());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest){
        MessageDTO messageDTO = commentService.createComment(commentRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        MessageDTO messageDTO = commentService.deleteComment(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
