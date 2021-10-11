package com.wildcats.project;

import com.wildcats.project.entity.User;
import com.wildcats.project.request.UserRequest;
import com.wildcats.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class ProjectApplication extends SpringBootServletInitializer {

//	private final UserService userService;
//
//	public ProjectApplication(UserService userService) {
//		this.userService = userService;
//	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void addUser() {
//		Optional<User> user = userService.getUserById(1);
//		System.out.println(user);
//		System.out.println(user.isPresent());
//		if (!user.isPresent()) {
//			UserRequest userRequest = new UserRequest();
//			userRequest.setCpf("18070872047");
//			userRequest.setEmail("iordan9663@uorak.com");
//			userRequest.setLogin("iordan9663@uorak.com");
//			userRequest.setName("Iordam Soarem Pacheco");
//			userRequest.setPassword("Toor80085");
//			userRequest.setPosition("Analista");
//			userRequest.setRole("admin");
//			userService.salvar(userRequest);
//		} else {
//			return;
//		}
//	}
}

