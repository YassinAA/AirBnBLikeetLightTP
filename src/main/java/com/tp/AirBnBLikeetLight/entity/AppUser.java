package com.tp.AirBnBLikeetLight.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "App_User", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "Last_Name", length = 36, nullable = false)
    private String lastName;

    @Column(name = "First_Name", length = 36, nullable = false)
    private String firstName;

    @Column(name = "User_Email", length = 100, nullable = false)
    @Email
    private String email;

    @Column(name = "Active", length = 1, nullable = false)
    private boolean active;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    public AppUser() {
    }


    public AppUser(String userName, String lastName, String firstName, @Email String email, boolean active, String encrytedPassword) {
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.active = active;
        this.encrytedPassword = encrytedPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
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

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", encrytedPassword='" + encrytedPassword + '\'' +
                '}';
    }
}