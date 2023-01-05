package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.ResultView;
import com.example.application.views.list.UserView;
import com.example.application.views.list.ATPView;
import com.example.application.views.list.WTAView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@Theme(themeFolder = "flowcrmtutorial")
@PWA(
    name = "Tenis Tournament",
    shortName = "TT",
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
        H1 logo = new H1("Vaadin Tennis Tournaments");
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
        RouterLink linkUsers = new RouterLink("Users", UserView.class);
        RouterLink listWTA = new RouterLink("WTA", WTAView.class);
        RouterLink listATP = new RouterLink("ATP", ATPView.class);
        RouterLink listResults = new RouterLink("Result", ResultView.class);
        linkUsers.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            linkUsers, listWTA, listATP, listResults
        ));
    }
}
