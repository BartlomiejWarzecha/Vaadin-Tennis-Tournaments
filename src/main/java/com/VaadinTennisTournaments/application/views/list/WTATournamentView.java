package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPTournament;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTATournament;
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
@Route(value = "WTA-Tournament", layout = MainLayout.class)
@PageTitle("WTA Tournament | Vaadin Tennis Tournaments")
@PermitAll
public class WTATournamentView extends VerticalLayout {
Grid<WTATournament> grid = new Grid<>(WTATournament.class);
TextField filterText = new TextField();
WTATournamentForm form;
MainService mainService;
HowToPlayView howToPlayView;

    public WTATournamentView(MainService mainService) {

        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("WTA-Tournament-view");
        setHeight("1300px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabResults();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(),
                getHrefParagraph("wtatennis.com/tournaments", "WTA Tournaments")
                , rules);
        updateList();
        closeEditor();
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
        form = new WTATournamentForm(mainService.findAllRanks());
        form.setWidth("25em");
        form.setHeight("400px");
        form.addListener(WTATournamentForm.SaveEvent.class, this::saveWTATournament);
        form.addListener(WTATournamentForm.DeleteEvent.class, this::deleteWTATournament);
        form.addListener(WTATournamentForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("WTA-Turnament-grid");
        grid.setSizeFull();

        grid.setColumns("tournament") ;
        grid.addColumn(result-> result.getRank().getName()).setHeader("Rank");
        grid.addColumn(WTATournament -> WTATournament.getDescription()).setHeader("Description");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editWTATournament(event.getValue()));
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Icon resultsIcon = new Icon(VaadinIcon.ARCHIVE);
        resultsIcon.setColor("black");

        Button addPredicionButton = new Button("Add WTA Tournament");
        addPredicionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addPredicionButton.addClickListener(click -> addWTATournament());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton, resultsIcon);
        toolbar.addClassName("toolbar-WTA");
        return toolbar;
    }
    private Paragraph getHrefParagraph(String hrefValue , String description){
        String pureHrefValue = hrefValue.replaceAll("\\s", "");// value without spaces
        Anchor href = new Anchor("https://www."+ pureHrefValue,  "here");
        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see details about " + description));
        return paragraph;
    }
    private void saveWTATournament(WTATournamentForm.SaveEvent event) {
        mainService.saveWTATournament(event.getWtaTournament());
        updateList();
        closeEditor();
    }
    private void deleteWTATournament(WTATournamentForm.DeleteEvent event) {
        mainService.deleteWTATournament(event.getWtaTournament());
        updateList();
        closeEditor();
    }
    public void editWTATournament(WTATournament wtaTournament) {
        if (wtaTournament == null) {
            closeEditor();
        } else {
            form.setWtaTournament(wtaTournament);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void addWTATournament() {
        grid.asSingleSelect().clear();
        editWTATournament(new WTATournament());
    }
    private void closeEditor() {
        form.setWtaTournament(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    private void updateList() {
        grid.setItems(mainService.findAllWTATournaments(filterText.getValue()));
    }
}