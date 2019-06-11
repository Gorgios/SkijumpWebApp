package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private UserRepository userRepository;

    @Autowired
    public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UniqueLoginValidator(){

    }
    public void initialize(UniqueLogin constraint) {
    }

    public boolean isValid(String login, ConstraintValidatorContext context) {
        boolean isOk = true;
        if (userRepository.findByUsername(login).orElse(null) != null)
            isOk=false;
        return login != null && isOk;

    }

}