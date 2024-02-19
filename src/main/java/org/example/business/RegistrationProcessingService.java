package org.example.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationProcessingService {
    private final OwnerService ownerService;
    private final UserService userService;


}
