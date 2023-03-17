package com.VaadinTennisTournaments.application.data.entity.register;

import com.VaadinTennisTournaments.application.data.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Entity
@Table(name = "registerUsers")
public class RegisterUser extends AbstractEntity {

    @NotEmpty(message = "Username can not be empty")
    @Column(unique = true)
    private String username;

    @Email
    @NotEmpty(message = "Password can not be empty")
    @Column(unique=true)
    private String email = "";

    private String roles = "User";

    @Size(min = 8, max = 64, message = "Password have from 8 to 64 characters")
    private String password;


    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}


    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
