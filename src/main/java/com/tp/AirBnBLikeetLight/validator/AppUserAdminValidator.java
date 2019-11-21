package com.tp.AirBnBLikeetLight.validator;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.form.AppUserAdminForm;
import com.tp.AirBnBLikeetLight.repository.AppUserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AppUserAdminValidator implements Validator {

    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private AppUserRepository appUserRepository;

    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppUserAdminForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUserAdminForm appUserAdminForm = (AppUserAdminForm) target;

        // Check the fields of AppUserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserAdminForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserAdminForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserAdminForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserAdminForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "encrytedPassword", "NotEmpty.appUserAdminForm.encrytedPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appUserAdminForm.confirmPassword");

        if (!this.emailValidator.isValid(appUserAdminForm.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "Pattern.appUserAdminForm.email");
        } else if (appUserAdminForm.getEmail() != null) {
            AppUser dbUser = appUserRepository.findByEmail(appUserAdminForm.getEmail());
            if (dbUser != null) {
                // Email has been used by another account.
                errors.rejectValue("email", "Duplicate.appUserAdminForm.email");
            }
        }

        if (!errors.hasFieldErrors("userName")) {
            AppUser dbUser = appUserRepository.findByUserName(appUserAdminForm.getUserName());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("userName", "Duplicate.appUserAdminForm.userName");
            }
        }

        if (!errors.hasErrors()) {
            if (!appUserAdminForm.getConfirmPassword().equals(appUserAdminForm.getEncrytedPassword())) {
                errors.rejectValue("confirmPassword", "Match.appUserAdminForm.confirmPassword");
            }
        }
    }

}
