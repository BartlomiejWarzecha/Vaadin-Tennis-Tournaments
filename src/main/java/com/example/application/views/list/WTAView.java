package com.example.application.views.list;
import com.example.application.data.entity.WTA.WTA;
import com.example.application.data.service.MainService;
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
@Route(value = "WTA", layout = MainLayout.class)
@PageTitle("WTA | Vaadin Tennis Tournaments")
@PermitAll
public class WTAView extends VerticalLayout {
    Grid<WTA> grid = new Grid<>(WTA.class);
    TextField filterText = new TextField();
    WTAForm form;
    MainService mainService;

    public WTAView(MainService mainService) {
        this.mainService = mainService;
        addClassName("wta-view");
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
    form = new WTAForm(mainService.findAllStages());
    form.setWidth("25em");
    form.addListener(WTAForm.SaveEvent.class, this::saveWTA);
    form.addListener(WTAForm.DeleteEvent.class, this::deleteWTA);
    form.addListener(WTAForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("wta-grid");
        grid.setSizeFull();
        grid.setColumns("nickname", "wtaTournament", "player");
        grid.addColumn(wta-> wta.getStage().getName()).setHeader("Stage");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editWTA(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPredicionButton = new Button("Add prediction");
        addPredicionButton.addClickListener(click -> addWTA());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton);
        toolbar.addClassName("toolbar-WTA");
        return toolbar;
    }

    private void saveWTA(WTAForm.SaveEvent event) {
        mainService.saveWTA(event.getWTA());
        updateList();
        closeEditor();
    }

    private void deleteWTA(WTAForm.DeleteEvent event) {
        mainService.deleteWTA(event.getWTA());
        updateList();
        closeEditor();
    }

    public void editWTA(WTA wta) {
        if (wta == null) {
            closeEditor();
        } else {
            form.setWTA(wta);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addWTA() {
        grid.asSingleSelect().clear();
        editWTA(new WTA());
    }

    private void closeEditor() {
        form.setWTA(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllWTA(filterText.getValue()));
    }
}
