package com.example.finalproject.user.dto;

import com.example.finalproject.user.entity.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String userName;
    String password;
    Set<UserRole> authorities;
}
