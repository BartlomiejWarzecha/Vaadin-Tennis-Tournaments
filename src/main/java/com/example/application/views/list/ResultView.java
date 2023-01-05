package com.example.application.views.list;
import com.example.application.data.entity.Result.Result;
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
@Route(value = "Result", layout = MainLayout.class)
@PageTitle("Results | Vaadin Tennis Tournaments")
@PermitAll
public class ResultView extends VerticalLayout {
    Grid<Result> grid = new Grid<>(Result.class);
    TextField filterText = new TextField();
    ResultForm form;
    MainService mainService;

    public ResultView(MainService mainService) {
        this.mainService = mainService;
        addClassName("result-view");
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
    form = new ResultForm(mainService.findAllInterests(), mainService.findAllRanks());
    form.setWidth("25em");
    form.addListener(ResultForm.SaveEvent.class, this::saveResult);
    form.addListener(ResultForm.DeleteEvent.class, this::deleteResult);
    form.addListener(ResultForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("result-grid");
        grid.setSizeFull();

        grid.setColumns("tournament", "winner");
        grid.addColumn(result-> result.getInterest().getName()).setHeader("Type");
        grid.addColumn(result-> result.getRank().getName()).setHeader("Rank");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editResult(event.getValue()));
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPredicionButton = new Button("Add result");
        addPredicionButton.addClickListener(click -> addResult());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton);
        toolbar.addClassName("toolbar-ATP");
        return toolbar;
    }

    private void saveResult(ResultForm.SaveEvent event) {
        mainService.saveResult(event.getResult());
        updateList();
        closeEditor();
    }

    private void deleteResult(ResultForm.DeleteEvent event) {
        mainService.deleteResult(event.getResult());

        updateList();
        closeEditor();
    }

    public void editResult(Result result) {
        if (result == null) {
            closeEditor();
        } else {
            form.setResult(result);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addResult() {
        grid.asSingleSelect().clear();
        editResult(new Result());
    }

    private void closeEditor() {
        form.setResult(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllResult(filterText.getValue()));
    }
}
