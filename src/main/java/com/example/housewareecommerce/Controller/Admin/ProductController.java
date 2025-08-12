package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import com.example.housewareecommerce.Model.Request.ProductRequest;
import com.example.housewareecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/admin/product")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        Page<ProductDTO> productDTOS = productService.getAll(pageNo);
        Integer totalPage = productDTOS.getTotalPages();
        ListDTO listDTO = new ListDTO();
        listDTO.setCurrentPage(pageNo);
        listDTO.setTotalPage(totalPage);
        listDTO.setData(productDTOS.getContent());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        MessageDTO messageDTO = productService.findById(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/product")
    public  ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        MessageDTO messageDTO = productService.createProduct(productRequest);

        if(messageDTO.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR){
            return new ResponseEntity<>(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
