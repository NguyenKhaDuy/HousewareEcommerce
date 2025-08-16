package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.EvaluateDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.EvaluateRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EvaluateService {
    Page<EvaluateDTO> getAllByProduct(Long productId, Integer pageNo);
    MessageDTO createRating(EvaluateRequest evaluteRequest);
    MessageDTO deleteRating(Long id);
}
