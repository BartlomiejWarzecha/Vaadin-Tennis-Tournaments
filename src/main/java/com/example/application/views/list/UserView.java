package com.example.application.views.list;

import com.example.application.data.entity.User.Interests;
import com.example.application.data.entity.User.Status;
import com.example.application.data.entity.User.User;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
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
@PageTitle("Users | Tennis Tournaments")
@PermitAll
public class UserView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    UserForm form;
    CrmService service;

    public UserView(CrmService service) {
        this.service = service;
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
    form = new UserForm(service.findAllInterests());
    form.setWidth("25em");
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
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add user");
        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveContact(UserForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(UserForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    public void editContact(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setContact(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new User());
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }
}
