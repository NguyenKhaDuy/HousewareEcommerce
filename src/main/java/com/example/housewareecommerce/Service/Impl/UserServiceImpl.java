package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.UserDTO;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Repository.UserRepository;
import com.example.housewareecommerce.Service.BryConfig;
import com.example.housewareecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataAccessException;
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

    @Value("${user.statusCode}")
    private Integer statusCode;

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

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return Optional.of(dto);
    }

    @Override
    public boolean login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .map(u -> BryConfig.matches(rawPassword, u.getPassword()))
                .orElse(false);
    }

    @Transactional
    @Override
    public boolean createUser(UserDTO user) {
        try {
            UserEntity entity = new UserEntity();
            entity.setName(user.getName());
            entity.setEmail(user.getEmail());
            entity.setPassword(BryConfig.hash(user.getPassword()));
            entity.setPhoneNumber(user.getPhoneNumber());
            entity.setGender(user.getGender());
            entity.setAddress(user.getAddress());
            entity.setCreated(LocalDateTime.now());
            entity.setRole(1L);

            Optional<StatusEntity> statusEntity = statusRepository.findById(Long.valueOf(statusCode));
            entity.setStatusEntity(statusEntity.orElseThrow(() -> new RuntimeException("Status not found")));

            userRepository.save(entity);

            return true;

        }catch (DataAccessException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updatePassword(String email, String newPassword) {
        try {
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                user.setPassword(BryConfig.hash(newPassword));
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error update password: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            return false;
        }
    }
}
