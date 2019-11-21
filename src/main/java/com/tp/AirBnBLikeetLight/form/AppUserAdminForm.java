package com.tp.AirBnBLikeetLight.form;

import com.tp.AirBnBLikeetLight.entity.AppUser;

public class AppUserAdminForm {
    private String userName;

    private String lastName;

    private String firstName;

    private String email;

    private boolean active;

    private String encrytedPassword;

    private String confirmPassword;

    public AppUserAdminForm() {
    }

    public AppUserAdminForm(AppUser appUser) {
        this.userName = appUser.getUserName();
        this.lastName = appUser.getLastName();
        this.firstName = appUser.getFirstName();
        this.email = appUser.getEmail();
        this.active = appUser.isActive();
        this.encrytedPassword = appUser.getEncrytedPassword();
        this.confirmPassword = confirmPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}