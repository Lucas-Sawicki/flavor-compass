package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.CustomerDAO;
import org.example.business.dao.OwnerDAO;
import org.example.domain.Customer;
import org.example.domain.Owner;
import org.example.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;


    @Transactional
    public Owner findOwner(String email) {
        Optional<Owner> owner = ownerDAO.findByEmail(email);
        if (owner.isEmpty()) {
            throw new NotFoundException("Could not find owner by email: [%s]".formatted(email));
        }
        return owner.get();
    }

    @Transactional
    public void saveOwner(Owner owner) {
        ownerDAO.saveOwner(owner);
    }

}
