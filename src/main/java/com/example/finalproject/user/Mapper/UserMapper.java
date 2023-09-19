package com.example.finalproject.user.Mapper;

import com.example.finalproject.user.dto.UserDto;
import com.example.finalproject.user.entity.User;

public class UserMapper {
    public static User toEntity(UserDto userDto){
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setAuthorities(userDto.getAuthorities());
        return user;
    }
}
