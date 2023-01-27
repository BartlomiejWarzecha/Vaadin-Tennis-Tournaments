package com.VaadinTennisTournaments.application.views;

import com.VaadinTennisTournaments.application.security.SecurityService;
import com.VaadinTennisTournaments.application.views.list.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        Button logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listHowToPlay = new RouterLink("How To Play", HowToPlayView.class);
        RouterLink linkUsers = new RouterLink("Users", UserView.class);
        RouterLink listWTA = new RouterLink("WTA", WTAView.class);
        RouterLink listATP = new RouterLink("ATP", ATPView.class);
        RouterLink listResults = new RouterLink("Results", ResultsView.class);
        RouterLink listWTAPunctation = new RouterLink("WTA Punctation", WTAPunctationView.class);
        RouterLink listATPPunctation = new RouterLink("ATP Punctation", ATPPunctationView.class);
        linkUsers.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            listHowToPlay, linkUsers, listWTA, listATP, listResults, listWTAPunctation, listATPPunctation
        ));
    }
}
