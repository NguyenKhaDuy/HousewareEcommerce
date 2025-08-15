package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.StatusRequest;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping(value = "/admin/status/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        MessageDTO messageDTO = statusService.getById(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/status")
    public ResponseEntity<?> createStatus(@RequestBody StatusRequest statusRequest){
        MessageDTO messageDTO = statusService.createStatus(statusRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/admin/status")
    public ResponseEntity<?> updateStatus(@RequestBody StatusRequest statusRequest){
        MessageDTO messageDTO = statusService.updateStatus(statusRequest);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id){
        MessageDTO messageDTO = statusService.deleteStatus(id);
        if(messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
