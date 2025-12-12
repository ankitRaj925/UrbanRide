package com.urbanride.urbanride.Dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest{
    private String name;
    private String email;
    private String password;
    private String role;
    private String phone;
}
