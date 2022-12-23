package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Stage extends AbstractEntity {
    @NotBlank
    private String name;

    public Stage() { }

    public Stage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
