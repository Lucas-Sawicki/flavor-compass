package org.example.api.management;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.api.dto.RegistrationDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistrationDTO user = (RegistrationDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
