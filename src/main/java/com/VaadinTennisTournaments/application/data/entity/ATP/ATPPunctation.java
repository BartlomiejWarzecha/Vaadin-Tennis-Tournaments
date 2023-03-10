package com.VaadinTennisTournaments.application.data.entity.ATP;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ATPPunctation extends AbstractEntity {


    @NotEmpty
    private String points;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    @NotNull
    @JsonIgnoreProperties({"ranks"})
    private Rank rank;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    @NotNull
    @JsonIgnoreProperties({"stages"})
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnoreProperties({"users"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "atpTournament_id")
    @NotNull
    @JsonIgnoreProperties({"atpTournament"})
    private ATP atpTournament;


    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ATP getAtpTournament() {
        return atpTournament;
    }

    public void setAtpTournament(ATP atpTournament) {
        this.atpTournament = atpTournament;
    }
}
