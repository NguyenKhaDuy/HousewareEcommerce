package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.UserDTO;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Repository.UserRepository;
import com.example.housewareecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Page<UserEntity> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public UserEntity updateUserById(Long id, UserDTO dto) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setAddress(dto.getAddress());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setGender(dto.getGender());
            user.setCreated(LocalDateTime.now());

            if (dto.getStatusId() != null) {
                StatusEntity status = statusRepository.findById(dto.getStatusId())
                        .orElseThrow(() -> new RuntimeException("Status not found"));
                user.setStatusEntity(status);
            }
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    @Transactional
    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
