package com.example.application.views.list;

import com.example.application.data.entity.User.User;
import com.example.application.data.service.MainService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
    TextField filterText = new TextField();
    UserForm form;
    MainService mainService;

    public UserView(MainService mainService) {
        this.mainService = mainService;
        addClassName("user-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

private void configureForm() {
    form = new UserForm(mainService.findAllInterests());
    form.setWidth("25em");
    form.setHeight("40em");
    form.addListener(UserForm.SaveEvent.class, this::saveContact);
    form.addListener(UserForm.DeleteEvent.class, this::deleteContact);
    form.addListener(UserForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("nickname", "email");
        grid.addColumn(user -> user.getInterest().getName()).setHeader("Interests");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by tournament...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addUserButton = new Button("Add user");
        addUserButton.addClickListener(click -> addContact());
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);


        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveContact(UserForm.SaveEvent event) {
        mainService.saveUser(event.getUser());
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
    }
}
