package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<StatusEntity> getAllStatus() {
        return statusRepository.findAll();
    }
}
