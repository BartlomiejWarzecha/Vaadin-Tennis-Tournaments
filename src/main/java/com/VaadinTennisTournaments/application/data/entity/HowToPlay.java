package com.VaadinTennisTournaments.application.data.entity;

import com.VaadinTennisTournaments.application.data.AbstractEntity;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPPlayer;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPTournament;
import com.VaadinTennisTournaments.application.data.entity.tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class HowToPlay extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnoreProperties({"users"})
    private User user;
    @Column(length = 1024)
    private String generalRulesDescription;
    @Column(length = 1024)
    private String usersDescription;
    @Column(length = 1024)
    private String predictionDescription;
    @Column(length = 1024)
    private String setupDescription;
    @Column(length = 1024)
    private String punctationDescription;
    @Column(length = 1024)
    private String resultsDescription;
    @Column(length = 1024)
    private String rankingDescription;


    public String getGeneralRulesDescription() {
        return generalRulesDescription;
    }

    public void setGeneralRulesDescription(String generalRulesDescription) {
        this.generalRulesDescription = generalRulesDescription;
    }

    public String getUsersDescription() {
        return usersDescription;
    }

    public void setUsersDescription(String usersDescription) {
        this.usersDescription = usersDescription;
    }

    public String getRankingDescription() {
        return rankingDescription;
    }

    public void setRankingDescription(String rankingDescription) {
        this.rankingDescription = rankingDescription;
    }
    public String getResultsDescription() {
        return resultsDescription;
    }
    public void setResultsDescription(String resultsDescription) {
        this.resultsDescription = resultsDescription;
    }
    public String getPunctationDescription() {
        return punctationDescription;
    }
    public void setPunctationDescription(String punctationDescription) {
        this.punctationDescription = punctationDescription;
    }
    public String getPredictionDescription() {
        return predictionDescription;
    }
    public void setPredictionDescription(String predictionDescription) {
        this.predictionDescription = predictionDescription;
    }
    public String getSetupDescription() {
        return setupDescription;
    }
    public void setSetupDescription(String setupDescription) {
        this.setupDescription = setupDescription;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}