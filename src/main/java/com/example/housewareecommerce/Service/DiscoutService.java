package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Entity.DiscountEntity;
import com.example.housewareecommerce.Model.DTO.DiscountDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.DiscountRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DiscoutService {
    Page<DiscountDTO> getAll(Integer pageOne);
    MessageDTO getById(Long id);
    MessageDTO createDisount(DiscountRequest discountRequest);
    MessageDTO updateDiscount(DiscountRequest discountRequest);
    MessageDTO deleteDiscount(Long id);

    Optional<Float> validateDiscountCode(String discountCode);
}
