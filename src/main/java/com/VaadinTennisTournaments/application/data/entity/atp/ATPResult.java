package com.VaadinTennisTournaments.application.data.entity.atp;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ATPResult extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @NotNull
    @JsonIgnoreProperties({"tournaments"})
    private ATPTournament tournament;

    @ManyToOne
    @JoinColumn(name = "atpplayer_id")
    @NotNull
    @JsonIgnoreProperties({"winners"})
    private ATPPlayer winner;


    public ATPTournament getTournament() {
        return tournament;
    }

    public void setTournament(ATPTournament tournament) {
        this.tournament = tournament;
    }

    public ATPPlayer getWinner() {
        return winner;
    }

    public void setWinner(ATPPlayer winner) {
        this.winner = winner;
    }
}

