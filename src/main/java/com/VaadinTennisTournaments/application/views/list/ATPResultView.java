package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATPResult;
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
@Route(value = "ATP-Results", layout = MainLayout.class)
@PageTitle("ATP Results | Vaadin Tennis Tournaments")
@PermitAll
public class ATPResultView extends VerticalLayout {
    Grid<ATPResult> grid = new Grid<>(ATPResult.class);
    TextField filterText = new TextField();
    ATPResultForm form;
    MainService mainService;
    HowToPlayView howToPlayView;

    public ATPResultView(MainService mainService, HowToPlayView howToPlayView) {
        this.mainService = mainService;
        this.howToPlayView = howToPlayView;

        addClassName("result-view");
        setHeight("1300px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabResults();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(), getHrefScoreParagraph("WTA Tennis", "ATP Tour"), rules);

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
    form = new ATPResultForm(mainService.findAllInterests(), mainService.findAllRanks());
    form.setWidth("25em");
    form.setHeight("400px");
    form.addListener(ATPResultForm.SaveEvent.class, this::saveATPResult);
    form.addListener(ATPResultForm.DeleteEvent.class, this::deleteATPResult);
    form.addListener(ATPResultForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("result-grid");
        grid.setSizeFull();

        grid.setColumns("tournament", "winner");
        grid.addColumn(result-> result.getRank().getName()).setHeader("Rank");
        grid.addColumn(result-> result.getInterest().getName()).setHeader("Type");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editATPResult(event.getValue()));
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
        addPredicionButton.addClickListener(click -> addATPResult());

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
    private void saveATPResult(ATPResultForm.SaveEvent event) {
        mainService.saveATPResult(event.getATPResult());
        updateList();
        closeEditor();
    }

    private void deleteATPResult(ATPResultForm.DeleteEvent event) {
        mainService.deleteATPResult(event.getATPResult());

        updateList();
        closeEditor();
    }

    public void editATPResult(ATPResult atpResult) {
        if (atpResult == null) {
            closeEditor();
        } else {
            form.setATPResult(atpResult);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addATPResult() {
        grid.asSingleSelect().clear();
        editATPResult(new ATPResult());
    }

    private void closeEditor() {
        form.setATPResult(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllATPResults(filterText.getValue()));
    }
}
