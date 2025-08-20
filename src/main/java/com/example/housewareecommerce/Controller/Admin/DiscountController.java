package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.DiscountDTO;
import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.DiscountRequest;
import com.example.housewareecommerce.Service.DiscoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController {
    @Autowired
    DiscoutService discoutService;

    @GetMapping(value = "/admin/discount")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        Page<DiscountDTO> discountDTOS = discoutService.getAll(pageNo);
        ListDTO<DiscountDTO> result = new ListDTO<>();
        result.setTotalPage(discountDTOS.getTotalPages());
        result.setCurrentPage(pageNo);
        result.setData(discountDTOS.getContent());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/discount/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        MessageDTO messageDTO = discoutService.getById(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/discount")
    public ResponseEntity<?> createDisount(@RequestBody DiscountRequest discountRequest){
        MessageDTO messageDTO = discoutService.createDisount(discountRequest);
        System.out.println("Code " + discountRequest.getDiscountCode());
        if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/admin/discount")
    public ResponseEntity<?> updateDiscount(@RequestBody DiscountRequest discountRequest){
        MessageDTO messageDTO = discoutService.updateDiscount(discountRequest);
        System.out.println("Code " + discountRequest.getDiscountCode());
        if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }else if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/discount/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id){
        MessageDTO messageDTO = discoutService.deleteDiscount(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
