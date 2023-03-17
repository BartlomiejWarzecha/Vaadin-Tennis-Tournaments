package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPTournament;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.dao.DataIntegrityViolationException;
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
    Grid<HowToPlay> ruleGrid = new Grid<>(HowToPlay.class);

    TextField filterText = new TextField();
    ATPTournamentForm form;
    MainService mainService;

    HowToPlayView howToPlayView;

    public ATPTournamentView(MainService mainService) {

        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("result-view");
        setHeight("1700px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabSetup();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(),
                getHrefParagraph("atptour.com/en/tournaments", "ATP Tournaments")
                ,rules, generateRulesGrid() );
        updateList();
        closeEditor();
    }
    private Grid generateRulesGrid(){
        ruleGrid.addClassNames("atp-tournament-rule-grid");
        ruleGrid.setSizeFull();
        ruleGrid.setColumns();
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getUser().getNickname()).setHeader("User");
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getSetupDescription())
                .setHeader("setup users rules:").setFlexGrow(1); // Set the flex grow value of this column to 1;
        ruleGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        ruleGrid.asSingleSelect().addValueChangeListener(event ->
                howToPlayView.editHowToPlay(event.getValue()));
        ruleGrid.setHeight("400px");
        ruleGrid.setWidthFull();

                return ruleGrid;
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
        Icon setupIcon = new Icon(VaadinIcon.COG);
        setupIcon.setColor("black");

        Button addATPTournamentButton = new Button("Add ATP Tournament");
        addATPTournamentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        addATPTournamentButton.addClickListener(click -> addATPTournament());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addATPTournamentButton, setupIcon);
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
                try{
                        mainService.deleteATPTournament(event.getAtpTournament());
                    }catch (
                        DataIntegrityViolationException e){
                        e.printStackTrace();
                       Notification notification = Notification
                                        .show("This tournament is used in another view!");
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        notification.setPosition(Notification.Position.MIDDLE);
                    }

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
        ruleGrid.setItems(mainService.findAllHowToPlay(filterText.getValue()));
    }
}

