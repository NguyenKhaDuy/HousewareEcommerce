package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.StatusRequest;

import java.util.Arrays;
import java.util.List;

public interface StatusService {
    List<StatusEntity> getAllStatus();
    MessageDTO getById(Long id);
    MessageDTO createStatus(StatusRequest statusRequest);
    MessageDTO updateStatus(StatusRequest statusRequest);
    MessageDTO deleteStatus(Long id);

    List<StatusEntity>findByIdIn(List<Long> ids);
}
