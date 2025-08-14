package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserEntity> getAllUsers(Pageable pageable);

    UserEntity updateUserById(Long id, UserDTO dto);

    boolean deleteUserById(Long id);

    Optional<UserEntity> getUserById(Long id);
}
