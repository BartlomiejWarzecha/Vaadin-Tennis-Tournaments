package com.VaadinTennisTournaments.application.data.entity.WTA;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class WTAPunctation extends AbstractEntity {

    @NotEmpty
    private String points;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnoreProperties({"users"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "wtaTournament_id")
    @NotNull
    @JsonIgnoreProperties({"wtaTournament"})
    private WTA wtaTournament;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WTA getWtaTournament() {
        return wtaTournament;
    }

    public void setWtaTournament(WTA wtaTournament) {
        this.wtaTournament = wtaTournament;
    }


}
