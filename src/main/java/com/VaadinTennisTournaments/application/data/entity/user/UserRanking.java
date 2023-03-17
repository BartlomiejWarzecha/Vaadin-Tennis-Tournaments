package com.VaadinTennisTournaments.application.data.entity.user;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.tournament.Interests;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class UserRanking extends AbstractEntity {


    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnoreProperties({"users"})
    private User user;
    @ManyToOne
    @JoinColumn(name = "interest_id")
    @NotNull
    @JsonIgnoreProperties({"interests"})
    private Interests interest;

    @Min(value = 0, message = "value must be at lest 1")
    private Integer points;

    @Min(value = 0, message = "value must be at lest 1")
    private Integer tournamentsNumber;

    public Interests getInterest() {
        return interest;
    }

    public void setInterest(Interests interest) {
        this.interest = interest;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getTournamentsNumber() {
        return tournamentsNumber;
    }

    public void setTournamentsNumber(Integer tournamentsNumber) {
        this.tournamentsNumber = tournamentsNumber;
    }
}
