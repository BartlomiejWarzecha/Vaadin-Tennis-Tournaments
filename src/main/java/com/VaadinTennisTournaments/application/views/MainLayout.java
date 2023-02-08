package com.VaadinTennisTournaments.application.views;

import com.VaadinTennisTournaments.application.security.SecurityService;
import com.VaadinTennisTournaments.application.views.list.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@Theme(themeFolder = "vaadinTennisTournament")
@PWA(
    name = "Vaadin Tennis Tournament",
    shortName = "Vaadin TT",
    offlinePath="offline.html",
    offlineResources = { "./images/offline.png"}
)
public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H3 logo = new H3("Vaadin Tennis Tournaments!");
        logo.addClassNames("text-l", "m-m");
        Icon outlineIcon = new Icon(VaadinIcon.MOON);
        outlineIcon.setColor("#ffff00");
        Button logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, outlineIcon, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {

        Icon grIcon = new Icon(VaadinIcon.BOOK);
        grIcon.setColor("black");
        Icon profileIcon = new Icon(VaadinIcon.USER);
        profileIcon.setColor("black");
        Icon atpWtaIcon = new Icon(VaadinIcon.SCALE);
        atpWtaIcon.setColor("black");
        Icon resultsIcon = new Icon(VaadinIcon.ARCHIVE);
        resultsIcon.setColor("black");
        Icon punctationIcon = new Icon(VaadinIcon.ABACUS);
        punctationIcon.setColor("black");
        Icon rankingIcon = new Icon(VaadinIcon.CHART);
        rankingIcon.setColor("black");
        Icon outlineIcon = new Icon(VaadinIcon.MOON);
        outlineIcon.setColor("#ffff00");

        RouterLink listHowToPlay = new RouterLink( "How To Play", HowToPlayView.class);
        RouterLink linkUsers = new RouterLink("Users", UserView.class);
        RouterLink linkUsersRanking = new RouterLink("Users Ranking", UserRankingView.class);
        RouterLink listWTAPrediction = new RouterLink("WTA Prediction", WTAPredictionView.class);
        RouterLink listWTAPunctation = new RouterLink("WTA Punctation", WTAPunctationView.class);
        RouterLink listWTAResults = new RouterLink("WTA Results", WTAResultView.class);
        RouterLink listATPPrediction = new RouterLink("ATP Prediction", ATPPredictionView.class);
        RouterLink listATPPunctation = new RouterLink("ATP Punctation", ATPPunctationView.class);
        RouterLink listATPResults = new RouterLink("ATP Results", ATPResultView.class);
        linkUsers.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
           outlineIcon, grIcon, listHowToPlay, profileIcon, linkUsers, atpWtaIcon, listWTAPrediction, listATPPrediction, punctationIcon, listWTAPunctation,
                 listATPPunctation, resultsIcon, listWTAResults, listATPResults, rankingIcon, linkUsersRanking, outlineIcon
        ));
    }
}
