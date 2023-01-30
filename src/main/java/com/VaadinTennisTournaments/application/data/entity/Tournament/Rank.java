package com.VaadinTennisTournaments.application.data.entity.Tournament;

import com.VaadinTennisTournaments.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Rank extends AbstractEntity {
    @NotBlank
    private String name;

    public Rank() { }

    public Rank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
