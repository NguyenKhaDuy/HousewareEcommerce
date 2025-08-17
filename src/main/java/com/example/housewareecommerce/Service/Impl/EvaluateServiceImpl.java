package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.EvaluateEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.EvaluateDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.EvaluateRequest;
import com.example.housewareecommerce.Repository.EvaluateRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Repository.UserRepository;
import com.example.housewareecommerce.Service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    EvaluateRepository evaluateRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Page<EvaluateDTO> getAllByProduct(Long productId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        ProductEntity productEntity = productRepository.findById(productId).get();
        Page<EvaluateEntity> evaluateEntities = evaluateRepository.findByProductEntity(productEntity, pageable);
        List<EvaluateDTO> evaluateDTOS = new ArrayList<>();
        for (EvaluateEntity it : evaluateEntities){
            EvaluateDTO evaluateDTO = new EvaluateDTO();
            evaluateDTO.setId(it.getId());
            evaluateDTO.setStar(it.getStar());
            evaluateDTO.setNote(it.getNote());
            evaluateDTO.setCreated(it.getCreated());
            evaluateDTO.setUserId(it.getUserEntity().getId());
            evaluateDTO.setUserName(it.getUserEntity().getName());
            evaluateDTOS.add(evaluateDTO);
        }
        return new PageImpl<>(evaluateDTOS, evaluateEntities.getPageable(), evaluateEntities.getTotalElements());
    }

    @Override
    public MessageDTO createRating(EvaluateRequest evaluteRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            ProductEntity productEntity = productRepository.findById(evaluteRequest.getProductId()).get();
            UserEntity userEntity = userRepository.findById(evaluteRequest.getUserId()).get();

            EvaluateEntity evaluateEntity = new EvaluateEntity();
            evaluateEntity.setNote(evaluteRequest.getNote());
            evaluateEntity.setCreated(LocalDate.now());
            evaluateEntity.setStar(evaluteRequest.getStar());
            evaluateEntity.setProductEntity(productEntity);
            evaluateEntity.setUserEntity(userEntity);

            EvaluateEntity evaluate = evaluateRepository.save(evaluateEntity);


            EvaluateDTO evaluateDTO = new EvaluateDTO();
            evaluateDTO.setId(evaluate.getId());
            evaluateDTO.setStar(evaluate.getStar());
            evaluateDTO.setNote(evaluate.getNote());
            evaluateDTO.setCreated(evaluate.getCreated());
            evaluateDTO.setUserId(evaluate.getUserEntity().getId());
            evaluateDTO.setUserName(evaluate.getUserEntity().getName());

            messageDTO.setMessage("Đánh giá thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(evaluateDTO);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không đánh giá thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteRating(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            EvaluateEntity evaluateEntity = evaluateRepository.findById(id).get();
            evaluateRepository.deleteById(id);
            messageDTO.setMessage("Xóa thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy đánh giá");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}