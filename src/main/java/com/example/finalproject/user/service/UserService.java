package com.example.finalproject.user.service;

import com.example.finalproject.user.Mapper.UserMapper;
import com.example.finalproject.user.dto.UserDto;
import com.example.finalproject.user.entity.User;
import com.example.finalproject.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    public void register(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        userRepository.save(user);
    }
}
