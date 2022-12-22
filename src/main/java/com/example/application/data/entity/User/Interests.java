package com.example.application.data.entity.User;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import com.example.application.data.AbstractEntity;

@Entity
public class Interests extends AbstractEntity {
    @NotBlank
    private String name;

    public Interests() { }

    public Interests(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
