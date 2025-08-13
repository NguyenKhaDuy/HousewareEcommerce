package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.ImageProductDTO;
import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
