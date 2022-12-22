package com.example.application.data.entity.WTA;

import com.example.application.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class WTA extends AbstractEntity {

    @NotEmpty
    private String Nickname = "";

    private String WTATournament;

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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getWTATournament() {
        return WTATournament;
    }

    public void setWTATournament(String WTATournament) {
        this.WTATournament = WTATournament;
    }
}
