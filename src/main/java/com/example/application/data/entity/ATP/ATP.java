package com.example.application.data.entity.ATP;

import com.example.application.data.AbstractEntity;
import com.example.application.data.entity.Stage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ATP extends AbstractEntity {

    @NotEmpty
    private String Nickname = "";

    @NotEmpty
    private String Player = "";

    @NotEmpty
    private String ATPTournament;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    @NotNull
    @JsonIgnoreProperties({"stages"})
    private Stage stage;

    @Override
    public String toString() {
        return '"' + Nickname + '"';
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        this.Nickname = nickname;
    }
    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getATPTournament() {
        return ATPTournament;
    }

    public void setATPTournament(String ATPTournament) {
        this.ATPTournament = ATPTournament;
    }
}
