package com.wildcats.project.mapper;

import com.wildcats.project.dto.UserDto;
import com.wildcats.project.entity.User;
import com.wildcats.project.request.UserRequest;
import com.wildcats.project.response.UserResponse;
import com.wildcats.project.response.UserResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public User toUser(@Valid UserRequest user){
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .position(user.getPosition())
                .cpf(user.getCpf())
                .role(user.getRole())
                .login(user.getLogin())
                .password(user.getPassword()).build();
    }

    public UserDto toDto(User user){
        return UserDto.builder()
                .cpf(user.getCpf())
                .name(user.getName())
                .position(user.getPosition())
                .role(user.getRole())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword()).build();
    }

    public List<UserDto> toListDto(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserResponse toSingleResult(User user) {
        return UserResponse.builder().user(this.toDto(user)).build();
    }

    public UserResponseList toListResult(List<User> users) {
        return UserResponseList.builder().users(this.toListDto(users)).build();
    }

}
