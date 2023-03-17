package com.VaadinTennisTournaments.application.data.entity.wta;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class WTATournament extends AbstractEntity {
    @NotEmpty
    private String tournament = "";
    @ManyToOne
    @JoinColumn(name = "rank_id")
    @NotNull
    @JsonIgnoreProperties({"ranks"})
    private Rank rank;
    private String description = "";

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

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}