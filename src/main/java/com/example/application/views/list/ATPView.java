package com.example.application.views.list;
import com.example.application.data.entity.ATP.ATP;
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
@Route(value = "ATP", layout = MainLayout.class)
@PageTitle("ATP | Tennis Tournaments")
@PermitAll
public class ATPView extends VerticalLayout {
    Grid<ATP> grid = new Grid<>(ATP.class);
    TextField filterText = new TextField();
    ATPForm form;
    CrmService service;

    public ATPView(CrmService service) {
        this.service = service;
        addClassName("atp-view");
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
    form = new ATPForm(service.findAllStages());
    form.setWidth("25em");
    form.addListener(ATPForm.SaveEvent.class, this::saveATP);
    form.addListener(ATPForm.DeleteEvent.class, this::deleteATP);
    form.addListener(ATPForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("atp-grid");
        grid.setSizeFull();
        grid.setColumns("nickname", "ATPTournament", "player");
        grid.addColumn(atp-> atp.getStage().getName()).setHeader("Stages");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editATP(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPredicionButton = new Button("Add prediction");
        addPredicionButton.addClickListener(click -> addATP());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton);
        toolbar.addClassName("toolbar-ATP");
        return toolbar;
    }

    private void saveATP(ATPForm.SaveEvent event) {
        service.saveATP(event.getATP());
        updateList();
        closeEditor();
    }

    private void deleteATP(ATPForm.DeleteEvent event) {
        service.deleteATP(event.getATP());

        updateList();
        closeEditor();
    }

    public void editATP(ATP atp) {
        if (atp == null) {
            closeEditor();
        } else {
            form.setATP(atp);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addATP() {
        grid.asSingleSelect().clear();
        editATP(new ATP());
    }

    private void closeEditor() {
        form.setATP(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllATP(filterText.getValue()));
    }
}
