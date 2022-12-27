package com.example.application.data.entity.Result;

import com.example.application.data.AbstractEntity;
import com.example.application.data.entity.Interests;
import com.example.application.data.entity.Rank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Result extends AbstractEntity {

    @NotEmpty
    private String Name = "";

    @NotEmpty
    private String Winner = "";

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
        return '"' + Name + '"';
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public String getWinner() {
        return Winner;
    }
    public void setWinner(String winner) {
        Winner = winner;
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
