package com.VaadinTennisTournaments.application.data.entity.wta;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class WTA extends AbstractEntity {


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
    @JoinColumn(name = "player_id")
    @NotNull
    @JsonIgnoreProperties({"players"})
    private WTAPlayer player;

    @ManyToOne
    @JoinColumn(name = "wtaTournament_id")
    @NotNull
    @JsonIgnoreProperties({"wtaTournaments"})
    private WTATournament wtaTournament;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WTAPlayer getPlayer() {
        return player;
    }

    public void setPlayer(WTAPlayer player) {
        this.player = player;
    }

    public WTATournament getWtaTournament() {
        return wtaTournament;
    }
    public String getWTATournamentName() {
        return wtaTournament.getTournament();
    }
    public void setWtaTournament(WTATournament wtaTournament) {
        this.wtaTournament = wtaTournament;
    }
}
