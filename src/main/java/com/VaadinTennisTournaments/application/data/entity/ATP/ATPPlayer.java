package com.VaadinTennisTournaments.application.data.entity.ATP;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ATPPlayer extends AbstractEntity {


    @NotEmpty
    private String fullname = "";

    private String description = "";

    public String getFullname() {
        return fullname;
    }
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnoreProperties({"users"})
    private User user;
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
