package com.VaadinTennisTournaments.application.data.entity.ATP;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.Stage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ATP extends AbstractEntity {

    @NotEmpty
    private String nickname = "";

    @NotEmpty
    private String player = "";

    @NotEmpty
    private String atpTournament;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    @NotNull
    @JsonIgnoreProperties({"stages"})
    private Stage stage;

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
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getAtpTournament() {
        return atpTournament;
    }

    public void setAtpTournament(String atpTournament) {
        this.atpTournament = atpTournament;
    }
}
