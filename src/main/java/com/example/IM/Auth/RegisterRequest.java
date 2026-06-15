package com.example.IM.Auth;


import com.example.IM.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String nombres;
    private String apellidos;
    @Builder.Default
    private Role role = Role.USER;
}