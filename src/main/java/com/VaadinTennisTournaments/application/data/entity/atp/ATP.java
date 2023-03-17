package com.VaadinTennisTournaments.application.data.entity.atp;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ATP extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnoreProperties({"users"})
    private User user;
    @ManyToOne
    @JoinColumn(name = "stage_id")
    @NotNull
    @JsonIgnoreProperties({"stages"})
    private Stage stage;
    @ManyToOne
    @JoinColumn(name = "player_id")
    @NotNull
    @JsonIgnoreProperties({"players"})
    private ATPPlayer player;
    @ManyToOne
    @JoinColumn(name = "atpTournament_id")
    @NotNull
    @JsonIgnoreProperties({"atpTournaments"})
    private ATPTournament atpTournament;

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

    public ATPPlayer getPlayer() {
        return player;
    }

    public void setPlayer(ATPPlayer player) {
        this.player = player;
    }

    public ATPTournament getAtpTournament() {
        return atpTournament;
    }

    public String getAtpTournamentName() {
        return atpTournament.getTournament();
    }

    public void setAtpTournament(ATPTournament atpTournament) {
        this.atpTournament = atpTournament;
    }
}
