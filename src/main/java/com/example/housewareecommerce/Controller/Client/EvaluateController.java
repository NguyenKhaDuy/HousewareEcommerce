package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Model.DTO.EvaluateDTO;
import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.EvaluateRequest;
import com.example.housewareecommerce.Service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EvaluateController {
    @Autowired
    EvaluateService evaluateService;
    @GetMapping(value = "/evaluate/{id}")
    public ResponseEntity<?> getAllByProduct(@PathVariable Long id, @RequestParam(name = "page", defaultValue = "1") Integer pageNo){
        Page<EvaluateDTO> evaluateDTOS = evaluateService.getAllByProduct(id, pageNo);
        ListDTO<EvaluateDTO> listDTO = new ListDTO<>();
        listDTO.setCurrentPage(pageNo);
        listDTO.setTotalPage(evaluateDTOS.getTotalPages());
        listDTO.setData(evaluateDTOS.getContent());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/evaluate")
    public ResponseEntity<?> createEvaluate(@RequestBody EvaluateRequest evaluateRequest){
        MessageDTO messageDTO = evaluateService.createRating(evaluateRequest);
        if (messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/evaluate/{id}")
    public ResponseEntity<?> createEvaluate(@PathVariable Long id){
        MessageDTO messageDTO = evaluateService.deleteRating(id);
        if (messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
