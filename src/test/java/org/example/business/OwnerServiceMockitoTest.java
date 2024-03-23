package org.example.business;

import org.example.business.dao.OwnerDAO;
import org.example.business.dao.UserDAO;
import org.example.domain.Owner;
import org.example.domain.User;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceMockitoTest {

    @Mock
    private OwnerDAO ownerDAO;
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    public void findOwnerByUser_ValidEmail_ShouldReturnOwner() {
        // given
        String email = "test@example.com";
        User user = OtherFixtures.someUser1();
        Owner owner = OtherFixtures.someOwner();
        when(userDAO.findByEmail(email)).thenReturn(Optional.of(user));
        when(ownerDAO.findOwnerByUser(user)).thenReturn(owner);

        // when
        Owner result = ownerService.findOwnerByUser(email);

        // then
        assertThat(result, is(owner));
    }

    @Test
    public void findOwnerById_ValidId_ShouldReturnOwner() {
        // given
        Integer id = 1;
        Owner owner = OtherFixtures.someOwner();
        when(ownerDAO.findOwnerById(id)).thenReturn(owner);

        // when
        Owner result = ownerService.findOwnerById(id);

        // then
        assertThat(result, is(owner));
    }

    @Test
    public void createOwner_ValidOwner_ShouldNotThrowException() {
        // given
        Owner owner = OtherFixtures.someOwner();
        when(ownerDAO.saveOwner(owner)).thenReturn(owner);

        // when
        ownerService.createOwner(owner);

        // then
        verify(ownerDAO).saveOwner(owner);
    }

    @Test
    public void findOwnerByEmail_ValidEmail_ShouldReturnOwner() {
        // given
        String email = "test@example.com";
        User user = OtherFixtures.someUser1();
        Owner owner = OtherFixtures.someOwner();
        User withOwner = user.withOwner(owner);
        when(userDAO.findByEmail(email)).thenReturn(Optional.of(withOwner));

        // when
        Owner result = ownerService.findOwnerByEmail(email);

        // then
        assertThat(result, is(owner));
    }

}