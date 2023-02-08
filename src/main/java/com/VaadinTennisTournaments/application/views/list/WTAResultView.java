package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAResult;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
@Route(value = "WTA-Results", layout = MainLayout.class)
@PageTitle("WTA Results | Vaadin Tennis Tournaments")
@PermitAll
public class WTAResultView extends VerticalLayout {
    Grid<WTAResult> grid = new Grid<>(WTAResult.class);
    TextField filterText = new TextField();
    WTAResultForm form;
    MainService mainService;

    public WTAResultView(MainService mainService) {
        this.mainService = mainService;
        addClassName("result-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent(), getHrefScoreParagraph("WTA Tennis", "ATP Tour"));
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
    form = new WTAResultForm(mainService.findAllInterests(), mainService.findAllRanks());
    form.setWidth("25em");
    form.setHeight("40em");
    form.addListener(WTAResultForm.SaveEvent.class, this::saveWTAResult);
    form.addListener(WTAResultForm.DeleteEvent.class, this::deleteWTAResult);
    form.addListener(WTAResultForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("result-grid");
        grid.setSizeFull();

        grid.setColumns("tournament", "winner");
        grid.addColumn(result-> result.getInterest().getName()).setHeader("Type");
        grid.addColumn(result-> result.getRank().getName()).setHeader("Rank");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editWTAResult(event.getValue()));
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Icon resultsIcon = new Icon(VaadinIcon.ARCHIVE);
        resultsIcon.setColor("black");

        Button addPredicionButton = new Button("Add result");
        addPredicionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addPredicionButton.addClickListener(click -> addResult());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton, resultsIcon);
        toolbar.addClassName("toolbar-ATP");
        return toolbar;
    }
    private Paragraph getHrefScoreParagraph(String value, String secondValue){

        String pureValue =  value.replaceAll("\\s", "");// value without spaces
        String pureSecondValue =  secondValue.replaceAll("\\s", "");// value without spaces

        Anchor firstHref = new Anchor("https://www."+pureValue+".com/scores",  value);
        Anchor secondHref = new Anchor("https://www."+pureSecondValue+".com/scores", secondValue);

        Paragraph paragraph = new Paragraph(new Text("See more information about different tournament results from official sites: "), firstHref, new Text(", "), secondHref);

        return paragraph;
    }
    private void saveWTAResult(WTAResultForm.SaveEvent event) {
        mainService.saveWTAResult(event.getWTAResult());
        updateList();
        closeEditor();
    }

    private void deleteWTAResult(WTAResultForm.DeleteEvent event) {
        mainService.deleteWTAResult(event.getWTAResult());

        updateList();
        closeEditor();
    }

    public void editWTAResult(WTAResult wtaResult) {
        if (wtaResult == null) {
            closeEditor();
        } else {
            form.setWTAResult(wtaResult);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addResult() {
        grid.asSingleSelect().clear();
        editWTAResult(new WTAResult());
    }

    private void closeEditor() {
        form.setWTAResult(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllWTAResults(filterText.getValue()));
    }
}
