package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.OwnerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Owner;
import org.example.domain.User;
import org.example.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;
    private final UserDAO userDAO;


    @Transactional
    public Owner findOwnerByEmail(String email) {
        Optional<User> owner = userDAO.findByEmail(email);
        if (owner.isEmpty()) {
            throw new NotFoundException("Could not find owner by email: [%s]".formatted(email));
        }
        return owner.get().getOwner();
    }

    @Transactional
    public Owner findOwnerById(Long id) {
        Optional<User> owner = userDAO.findOwnerById(id);
        if (owner.isEmpty()) {
            throw new NotFoundException("Could not find owner by id: [%s]".formatted(id));
        }
        return owner.get().getOwner();
    }

    @Transactional
    public Owner saveOwner(Owner owner) {
        return ownerDAO.saveOwner(owner);

    }

}
