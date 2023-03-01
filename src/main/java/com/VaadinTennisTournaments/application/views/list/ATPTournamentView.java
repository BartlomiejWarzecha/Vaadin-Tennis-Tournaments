package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPResult;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATPTournament;
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
@Route(value = "ATP-Tournament", layout = MainLayout.class)
@PageTitle("ATP Tournament | Vaadin Tennis Tournaments")
@PermitAll
public class ATPTournamentView extends VerticalLayout {
    Grid<ATPTournament> grid = new Grid<>(ATPTournament.class);
    TextField filterText = new TextField();
    ATPTournamentForm form;
    MainService mainService;

    HowToPlayView howToPlayView;

    public ATPTournamentView(MainService mainService) {

        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("result-view");
        setHeight("1300px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabResults();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(), getHrefParagraph("atptour.com/en/tournaments", "ATP Tournaments")
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
        form = new ATPTournamentForm(mainService.findAllRanks());
        form.setWidth("25em");
        form.setHeight("400px");
        form.addListener(ATPTournamentForm.SaveEvent.class, this::saveATPTournament);
        form.addListener(ATPTournamentForm.DeleteEvent.class, this::deleteATPTournament);
        form.addListener(ATPTournamentForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("result-grid");
        grid.setSizeFull();

        grid.setColumns("tournament");
        grid.addColumn(result -> result.getRank().getName()).setHeader("Rank");
        grid.addColumn(ATPTournament -> ATPTournament.getDescription()).setHeader("Description");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editATPTournament(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Icon resultsIcon = new Icon(VaadinIcon.ARCHIVE);
        resultsIcon.setColor("black");

        Button addATPTournamentButton = new Button("Add ATP Tournament");
        addATPTournamentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        addATPTournamentButton.addClickListener(click -> addATPTournament());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addATPTournamentButton, resultsIcon);
        toolbar.addClassName("toolbar-ATP");
        return toolbar;
    }

    private Paragraph getHrefParagraph(String hrefValue, String description) {
        String pureHrefValue = hrefValue.replaceAll("\s", "");// value without spaces
        Anchor href = new Anchor("https://www." + pureHrefValue, "here");
        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see details about " + description));
        return paragraph;
    }

    private void saveATPTournament(ATPTournamentForm.SaveEvent event) {
        mainService.saveATPTournament(event.getAtpTournament());
        updateList();
        closeEditor();
    }

    private void deleteATPTournament(ATPTournamentForm.DeleteEvent event) {
        mainService.deleteATPTournament(event.getAtpTournament());
        updateList();
        closeEditor();
    }

    public void editATPTournament(ATPTournament atpTournament) {
        if (atpTournament == null) {
            closeEditor();
        } else {
            form.setAtpTournament(atpTournament);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addATPTournament() {
        grid.asSingleSelect().clear();
        editATPTournament(new ATPTournament());
    }

    private void closeEditor() {
        form.setAtpTournament(null);
        form.setVisible(false);
    removeClassName("editing");
}

    private void updateList() {
        grid.setItems(mainService.findAllATPTournaments(filterText.getValue()));
    }
}

