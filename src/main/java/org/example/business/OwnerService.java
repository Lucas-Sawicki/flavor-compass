package org.example.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.business.dao.OwnerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Owner;
import org.example.domain.User;
import org.example.domain.exception.CustomException;
import org.example.domain.exception.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;
    private final UserDAO userDAO;


    @Transactional
    public Owner findOwnerByUser(String email) {
        try {
            User user = userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
            return ownerDAO.findOwnerByUser(user);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while accessing data.", ex.getMessage());
        }
    }

    @Transactional
    public Owner findOwnerById(Integer id) {
        try {
            return ownerDAO.findOwnerById(id);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while finding owner by id.", ex.getMessage());
        }
    }

    @Transactional
    public void createOwner(Owner owner) {
        try {
            ownerDAO.saveOwner(owner);
        } catch (DataAccessException ex) {
            throw new CustomException("Error while saving owner.", ex.getMessage());
        }
    }

    @Transactional
    public Owner findOwnerByEmail(String email) {
        try {
            User owner = userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
            return owner.getOwner();
        } catch (DataAccessException ex) {
            throw new CustomException("Error finding owner by email.", ex.getMessage());
        }
    }
}
