package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.UserDTO;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();

    UserEntity updateUserById(Long id, UserDTO dto);

    boolean deleteUserById(Long id);
}
