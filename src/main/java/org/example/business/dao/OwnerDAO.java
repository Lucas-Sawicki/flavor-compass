package org.example.business.dao;

import org.example.domain.Owner;
import org.example.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerDAO {

    Owner saveOwner(Owner owner);

    Owner findOwnerById(Integer id);

    Owner findOwnerByUser(User user);
}
