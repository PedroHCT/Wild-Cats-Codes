package com.wildcats.project.controller;

import com.wildcats.project.dto.UserDto;
import com.wildcats.project.entity.User;
import com.wildcats.project.mapper.UserMapper;
import com.wildcats.project.request.Login;
import com.wildcats.project.request.UserRequest;
import com.wildcats.project.response.UserResponse;
import com.wildcats.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAll(){
        List<User> users = this.userService.getAll();
        return userMapper.toListDto(users);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Integer id){
        return this.userService.getUserById(id);
    }

    @PostMapping("/login")
    public UserResponse getUser(@RequestBody @Valid Login login){
        return this.userService.getUserByPassAndLogin(login.getLogin().toLowerCase(Locale.ROOT), login.getPassword());
    }

    @PostMapping
    public UserResponse save(@RequestBody @Valid UserRequest request) {
        User user = this.userService.salvar(request);
        return userMapper.toSingleResult(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Integer id){
        this.userService.deleteUSer(id);
    }

    @DeleteMapping("/DELETE-ALL")
    public void deleteAll(){
        this.userService.deletAllUsers();
    }
}
