package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.User.UserRanking;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;

@Component
@Scope("prototype")
@Route(value = "UserRanking", layout = MainLayout.class)
@PageTitle("Users Ranking | Vaadin Tennis Tournaments")
@PermitAll
public class UserRankingView extends VerticalLayout {
    Grid<UserRanking> grid = new Grid<>(UserRanking.class);
    TextField filterText = new TextField();
    UserRankingForm form;
    MainService mainService;
    HowToPlayView howToPlayView;

    public UserRankingView(MainService mainService) {
        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("user-ranking-view");
        setHeight("1300px");
        setWidthFull();

        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabRanking();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(), rules);
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setWidthFull();
        content.setHeight("400px");
        return content;
    }

private void configureForm() {
    form = new UserRankingForm(mainService.findAllInterests(), mainService.findAllUsers("" ));
    form.setWidth("25em");
    form.setHeight("400px");
    form.addListener(UserRankingForm.SaveEvent.class, this::saveUserRanking);
    form.addListener(UserRankingForm.DeleteEvent.class, this::deleteUserRanking);
    form.addListener(UserRankingForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("tournamentsNumber");
        grid.addColumn(userRanking -> userRanking.getPoints()).setHeader("Points");
        grid.addColumn(userRanking -> userRanking.getUser().getNickname()).setHeader("User");
        grid.addColumn(userRanking -> userRanking.getUser().getInterest().getName()).setHeader("Interest");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editUserRanking(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Icon rankingIcon = new Icon(VaadinIcon.CHART);
        rankingIcon.setColor("black");

        Button addUserButton = new Button("Add ranking for user ");
        addUserButton.addClickListener(click -> addUserRanking());
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);


        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton, rankingIcon);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveUserRanking(UserRankingForm.SaveEvent event) {
        mainService.saveUserRanking(event.getUserRanking());
        updateList();
        closeEditor();
    }

    private void deleteUserRanking(UserRankingForm.DeleteEvent event) {
        mainService.deleteUserRanking(event.getUserRanking());
        updateList();
        closeEditor();
    }

    public void editUserRanking(UserRanking userRanking) {
        if (userRanking == null) {
            closeEditor();
        } else {
            form.setUserRanking(userRanking);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addUserRanking() {
        grid.asSingleSelect().clear();
        editUserRanking(new UserRanking());
    }

    private void closeEditor() {
        form.setUserRanking(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllUserRankings(filterText.getValue()));
    }
}
