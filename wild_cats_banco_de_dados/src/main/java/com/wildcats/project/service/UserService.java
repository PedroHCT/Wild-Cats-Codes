package com.wildcats.project.service;

import com.wildcats.project.entity.User;
import com.wildcats.project.mapper.UserMapper;
import com.wildcats.project.request.UserRequest;
import com.wildcats.project.response.UserResponse;
import com.wildcats.project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User salvar(UserRequest userRequest) {
        userRequest.setLogin(userRequest.getLogin().toLowerCase(Locale.ROOT));
        User user = this.userRepository.save(this.userMapper.toUser(userRequest));

        return user;
    }

    public UserResponse getUserByPassAndLogin(String login, String password) {
        UserResponse userResponse = userMapper.toSingleResult(this.userRepository.findUserByLoginAndPassword(login, password));
        return userResponse;
    }

    public Optional<User> getUserById(Integer id) {
        return this.userRepository.findById(id);
    }

    public void deleteUSer(Integer id) {
        this.userRepository.deleteById(id);
    }

    public void deletAllUsers() {
        this.userRepository.deleteAll();
    }

}
