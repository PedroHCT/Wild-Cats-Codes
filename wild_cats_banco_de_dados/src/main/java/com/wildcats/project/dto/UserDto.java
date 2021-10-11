package com.wildcats.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String cpf;

    private String name;

    private String position;

    private String role;

    private String email;

    private String login;

    private String password;
}
