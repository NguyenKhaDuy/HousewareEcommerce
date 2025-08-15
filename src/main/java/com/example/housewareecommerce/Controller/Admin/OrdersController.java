package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.OrdersDTO;
import com.example.housewareecommerce.Model.Request.OrdersRequest;
import com.example.housewareecommerce.Service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @GetMapping(value = "/admin/orders")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        Page<OrdersDTO> ordersDTOS = ordersService.getAll(pageNo);
        ListDTO<OrdersDTO> listDTO = new ListDTO<>();
        listDTO.setTotalPage(ordersDTOS.getTotalPages());
        listDTO.setCurrentPage(pageNo);
        listDTO.setData(ordersDTOS.getContent());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/orders/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        MessageDTO messageDTO = ordersService.getById(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<?> createOrders(@RequestBody OrdersRequest ordersRequest){
        MessageDTO messageDTO = ordersService.createOrders(ordersRequest);
        if (messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
