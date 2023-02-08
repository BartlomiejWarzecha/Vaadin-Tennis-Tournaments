package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPlayer;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
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
@Route(value = "ATPPlayer", layout = MainLayout.class)
@PageTitle("ATP Player | Vaadin Tennis Tournaments")
@PermitAll
public class ATPPlayerView extends VerticalLayout {
    Grid<ATPPlayer> grid = new Grid<>(ATPPlayer.class);
    TextField filterText = new TextField();
    ATPPlayerForm form;
    MainService mainService;

    public ATPPlayerView(MainService mainService) {
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
    form = new ATPPlayerForm(mainService.findAllUsers( "ATP"));
    form.setWidth("25em");
    form.setHeight("40em");
    form.addListener(ATPPlayerForm.SaveEvent.class, this::saveATPPlayer);
    form.addListener(ATPPlayerForm.DeleteEvent.class, this::deleteATPPlayer);
    form.addListener(ATPPlayerForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("fullname", "description");
        grid.addColumn(user -> user.getUser().getNickname()).setHeader("User");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editATPPlayer(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addUserButton = new Button("Add ATP Player");
        addUserButton.addClickListener(click -> addATPPlayer());
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);


        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveATPPlayer(ATPPlayerForm.SaveEvent event) {
        mainService.saveATPPlayer(event.getAtpPlayer());
        updateList();
        closeEditor();
    }

    private void deleteATPPlayer(ATPPlayerForm.DeleteEvent event) {
        mainService.deleteATPPlayer(event.getAtpPlayer());
        updateList();
        closeEditor();
    }

    public void editATPPlayer(ATPPlayer atpPlayer) {
        if (atpPlayer == null) {
            closeEditor();
        } else {
            form.setAtpPlayer(atpPlayer);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addATPPlayer() {
        grid.asSingleSelect().clear();
        editATPPlayer(new ATPPlayer());
    }

    private void closeEditor() {
        form.setAtpPlayer(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllATPPlayers(filterText.getValue()));
    }
}
