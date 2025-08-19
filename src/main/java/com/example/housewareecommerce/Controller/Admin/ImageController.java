package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.ImageProductDTO;
import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.ImageRequest;
import com.example.housewareecommerce.Service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {
    @Autowired
    ImageProductService imageProductService;

    @GetMapping(value = "/admin/image")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        Page<ImageProductDTO> imageProductDTOS = imageProductService.getAll(pageNo);
        ListDTO<ImageProductDTO> listDTO = new ListDTO<>();
        listDTO.setTotalPage(imageProductDTOS.getTotalPages());
        listDTO.setCurrentPage(pageNo);
        listDTO.setData(imageProductDTOS.getContent());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/image/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        MessageDTO messageDTO = imageProductService.getById(id);
        if (messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/image")
    public ResponseEntity<?> createImage(@ModelAttribute ImageRequest imageRequest){
        MessageDTO messageDTO = imageProductService.createImage(imageRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/admin/image/{imageId}")
    public ResponseEntity<?> updateImage(
            @PathVariable Long imageId,
            @RequestParam("newImage") MultipartFile newImage) {

        MessageDTO messageDTO = imageProductService.updateImage(imageId, newImage);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/image")
    public ResponseEntity<?> deleteImage(@RequestParam(name = "id") Long id){
        System.out.println("Delete image called with ID: " + id);
        MessageDTO messageDTO = imageProductService.deleteImage(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
