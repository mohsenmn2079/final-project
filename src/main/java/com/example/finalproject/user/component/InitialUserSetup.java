package com.example.finalproject.user.component;

import com.example.finalproject.user.Mapper.UserMapper;
import com.example.finalproject.user.dto.UserDto;
import com.example.finalproject.user.entity.User;
import com.example.finalproject.user.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

@Component
public class InitialUserSetup {
    private final UserRepository userRepository;

    public InitialUserSetup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialUser() {
        if (!userRepository.findUserByUserName("initialUser").isPresent()) {
            Yaml yaml = new Yaml(new Constructor(UserDto.class));
            try {
                UserDto initialUser = yaml.load(new ClassPathResource("initial-user.yml").getInputStream());
                userRepository.save(UserMapper.toEntity(initialUser));
                System.out.println("Initial user created.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
