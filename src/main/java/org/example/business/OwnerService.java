package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.OwnerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Owner;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;
    private final UserDAO userDAO;


    @Transactional
    public Owner findOwnerByUser(String email) {
        User user = userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));
        Owner owner = ownerDAO.findOwnerByUser(user);
        return owner;
    }

    @Transactional
    public Owner findOwnerById(Integer id) {
        return ownerDAO.findOwnerById(id);
    }

    @Transactional
    public Owner createOwner(Owner owner) {
        return ownerDAO.saveOwner(owner);

    }

}
