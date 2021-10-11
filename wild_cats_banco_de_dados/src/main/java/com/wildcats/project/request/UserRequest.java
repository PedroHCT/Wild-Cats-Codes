package com.wildcats.project.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Integer id;

    private String cpf;

    private String name;

    private String position;

    private String role;

    private String email;

    private String login;

    private String password;
}
