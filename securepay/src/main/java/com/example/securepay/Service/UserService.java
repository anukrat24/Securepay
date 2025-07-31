package com.example.securepay.Service;

import com.example.securepay.Dto.LoginDTO;
import com.example.securepay.Dto.UserDTO;
import com.example.securepay.Dto.AuthResponse;
import com.example.securepay.Entity.User;

public interface UserService {
    AuthResponse registerUser(UserDTO userDTO);
    AuthResponse loginUser(LoginDTO loginDTO);
    User getUserByEmail(String email);
}
