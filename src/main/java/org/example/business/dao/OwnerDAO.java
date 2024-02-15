package org.example.business.dao;

import org.example.domain.Customer;
import org.example.domain.Owner;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerDAO {

    void saveOwner(Owner owner);

}
