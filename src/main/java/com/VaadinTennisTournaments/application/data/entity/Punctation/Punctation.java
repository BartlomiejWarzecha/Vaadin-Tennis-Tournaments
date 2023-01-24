package com.VaadinTennisTournaments.application.data.entity.Punctation;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.Interests;
import com.VaadinTennisTournaments.application.data.entity.Rank;
import com.VaadinTennisTournaments.application.data.entity.Stage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Punctation extends AbstractEntity {

    @NotEmpty
    private String tournament = "";

    @NotEmpty
    private String nickname = "";

    @NotEmpty
    private String points;
    @ManyToOne
    @JoinColumn(name = "interest_id")
    @NotNull
    @JsonIgnoreProperties({"interests"})
    private Interests interest;

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
    @Override
    public String toString() {
        return '"' + tournament + '"';
    }
    public String getTournament() {
        return tournament;
    }
    public void setTournament(String Name) {
        this.tournament = Name;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String Nickname) {
        this.nickname = Nickname;
    }
    public Interests getInterest() {
        return interest;
    }

    public void setInterest(Interests interest) {
        this.interest = interest;
    }

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
}
