package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.api.dto.RegistrationDTO;
import org.example.api.dto.rest.RestOwnerDTO;
import org.example.business.dao.UserDAO;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.domain.exception.CustomException;
import org.example.infrastructure.database.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final RoleService roleService;


    public Optional<User> findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be null or empty");
        }
        return userDAO.findByEmail(email);
    }

    public Optional<UserEntity> findEntityByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be null or empty");
        }
        return userDAO.findEntityByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be null or empty");
        }
        return userDAO.existsByEmail(email);
    }

    public void createUser(User user) {
        if (user == null) {
            throw new CustomException("User cannot be null");
        }
        userDAO.saveUser(user);
    }

    @Transactional
    public void setAsOwner(User user, RestOwnerDTO body) {
        String role_owner = "ROLE_OWNER";
        Set<Role> roles = user.getRoles().stream()
                .filter(role -> role.getRole().equals(role_owner))
                .collect(Collectors.toSet());
        if (roles.isEmpty()) {
            Role role = roleService.findByRole(role_owner);
            user.getRoles().add(role);
            userDAO.setAsOwner(user, body);
        } else {
            throw new CustomException("User already have this role");
        }
    }

    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }
}
