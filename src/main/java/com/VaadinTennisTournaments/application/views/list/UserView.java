package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.user.User;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Users | Vaadin Tennis Tournaments")
@PermitAll
public class UserView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class);
    Grid<HowToPlay> ruleGrid = new Grid<>(HowToPlay.class);

    TextField filterText = new TextField();
    UserForm form;
    MainService mainService;
    HowToPlayView howToPlayView;

    public UserView(MainService mainService) {
        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);
        addClassName("user-view");
        setHeight("1700px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabPrediction();
        HorizontalLayout rules = new HorizontalLayout(tab);

        Span s = new Span("*You can only add predictions for chosen interest!");
        s.getStyle().set("font-size", "15px");

        add(getToolbar(), getContent(), s, rules, generateRulesGrid());
        updateList();
        closeEditor();
    }
    private Grid generateRulesGrid(){
        ruleGrid.addClassNames("rule-grid");
        ruleGrid.setSizeFull();
        ruleGrid.setColumns();
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getUser().getNickname()).setHeader("User");
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getUsersDescription()).setHeader("profiles users rules:").setFlexGrow(1); // Set the flexgrow value of this column to 1;
        ruleGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        ruleGrid.asSingleSelect().addValueChangeListener(event ->
                howToPlayView.editHowToPlay(event.getValue()));
        ruleGrid.setHeight("400px");
        ruleGrid.setWidthFull();

                return ruleGrid;
        }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setHeight("400px");
        content.setWidthFull();
        return content;
    }

private void configureForm() {
    form = new UserForm(mainService.findAllInterests());
    form.setWidth("25em");
    form.setHeight("400px");
    form.addListener(UserForm.SaveEvent.class, this::saveContact);
    form.addListener(UserForm.DeleteEvent.class, this::deleteContact);
    form.addListener(UserForm.CloseEvent.class, e -> closeEditor());
}


    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("nickname", "email");
        grid.addColumn(user -> user.getInterest().getName()).setHeader("Interests");
        grid.addColumn(user -> user.getDescription()).setHeader("Description");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Icon profileIcon = new Icon(VaadinIcon.USER);
                profileIcon.setColor("black");


        Button addUserButton = new Button("Add profile");
        addUserButton.addClickListener(click -> addContact());
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton, profileIcon);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveContact(UserForm.SaveEvent event) {
        mainService.saveUser(event.getUser());

        Button test = new Button("test");
        test.setEnabled(true);

        Notification notification = Notification
                .show("Welcome, " + event.getUser().getNickname() +  "! Have Fun!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);

        notification.addDetachListener(detachevent -> test.setEnabled(true));

        updateList();
        closeEditor();
    }

    private void deleteContact(UserForm.DeleteEvent event) {
        mainService.deleteUser(event.getUser());
        updateList();
        closeEditor();
    }

    public void editContact(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new User());
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllUsers(filterText.getValue()));
        ruleGrid.setItems(mainService.findAllHowToPlay(filterText.getValue()));
    }
}
