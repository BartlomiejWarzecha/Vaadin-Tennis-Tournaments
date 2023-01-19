package com.VaadinTennisTournaments.application.data.entity.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.Interests;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class User extends AbstractEntity {

    @NotEmpty
    private String nickname = "";

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @NotNull
    @JsonIgnoreProperties({"interests"})
    private Interests interests;

    @Email
    @NotEmpty
    private String email = "";

    @Override
    public String toString() {
        return '"' + nickname + '"';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Interests getInterest() {
        return interests;
    }

    public void setInterest(Interests interests) {
        this.interests = interests;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
