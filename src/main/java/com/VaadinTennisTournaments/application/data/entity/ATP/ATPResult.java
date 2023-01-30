package com.VaadinTennisTournaments.application.data.entity.ATP;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ATPResult extends AbstractEntity {

    @NotEmpty
    private String tournament = "";

    @NotEmpty
    private String winner = "";

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

    public String getWinner() {
        return winner;
    }
    public void setWinner(String Winner) {
        this.winner = Winner;
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
}
