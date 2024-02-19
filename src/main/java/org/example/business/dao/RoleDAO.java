package org.example.business.dao;

import org.example.domain.Role;

public interface RoleDAO {
    Role saveRole(Role role);
    Role findByRole(String role);
}
