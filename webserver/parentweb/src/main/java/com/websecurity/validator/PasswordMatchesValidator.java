package com.websecurity.validator;

import com.websecurity.models.AppUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        AppUserDto user = (AppUserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
