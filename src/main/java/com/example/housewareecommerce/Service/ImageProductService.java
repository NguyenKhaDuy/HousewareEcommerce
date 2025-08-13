package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.ImageProductDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ImageProductService {
    Page<ImageProductDTO> getAll(Integer pageNo);
    MessageDTO getById(Long id);
}
