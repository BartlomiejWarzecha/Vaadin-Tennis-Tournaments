package com.VaadinTennisTournaments.application.data.entity.wta;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.tournament.Interests;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class WTAResult extends AbstractEntity {


    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @NotNull
    @JsonIgnoreProperties({"tournaments"})
    private WTATournament tournament;

    @ManyToOne
    @JoinColumn(name = "wtaplayer_id")
    @NotNull
    @JsonIgnoreProperties({"winners"})
    private WTAPlayer winner;


    public WTAPlayer getWinner() {
        return winner;
    }

    public void setWinner(WTAPlayer winner) {
        this.winner = winner;
    }

    public WTATournament getTournament() {
        return tournament;
    }

    public void setTournament(WTATournament tournament) {
        this.tournament = tournament;
    }
}
