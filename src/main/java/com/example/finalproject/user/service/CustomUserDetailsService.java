package com.example.finalproject.user.service;

import com.example.finalproject.user.Mapper.UserMapper;
import com.example.finalproject.user.dto.UserDto;
import com.example.finalproject.user.entity.User;
import com.example.finalproject.user.exeption.UserNotFoundException;
import com.example.finalproject.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(username)
                .orElseThrow(UserNotFoundException::new);
    }
}



