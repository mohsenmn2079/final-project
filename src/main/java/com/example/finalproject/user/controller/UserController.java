package com.example.finalproject.user.controller;

import com.example.finalproject.user.dto.UserDto;
import com.example.finalproject.user.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String>register(@RequestBody UserDto userDto){
        userService.register(userDto);
        return ResponseEntity.ok("register successful");
    }
}
