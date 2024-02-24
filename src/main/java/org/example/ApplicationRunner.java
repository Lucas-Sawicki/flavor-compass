package org.example;

import org.example.domain.Role;
import org.example.domain.User;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.RoleRepository;
import org.example.infrastructure.database.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

//    @Bean
//    CommandLineRunner run(
//            RoleRepository roleRepository,
//            UserRepository userRepository,
//            PasswordEncoder encoder) {
//        return args -> {
//            if (userRepository.findByEmail("admin").isPresent()){
//                return;
//            }
//            RoleEntity role = roleRepository.findByRole("ADMIN");
//            Set<RoleEntity> roles = new HashSet<>();
//            roles.add(role);
//            String password = encoder.encode("admin");
//            UserEntity admin = new UserEntity(1, "admin", password, true, null, null, roles);
//            userRepository.saveUser(admin);
//        };
//    }

}
