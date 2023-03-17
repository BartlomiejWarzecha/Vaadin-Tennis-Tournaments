package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPPlayer;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value = "ATPPlayer", layout = MainLayout.class)
@PageTitle("ATP Player | Vaadin Tennis Tournaments")
@PermitAll
public class ATPPlayerView extends VerticalLayout {
    Grid<ATPPlayer> grid = new Grid<>(ATPPlayer.class);
    Grid<HowToPlay> ruleGrid = new Grid<>(HowToPlay.class);
    TextField filterText = new TextField();
    ATPPlayerForm form;
    MainService mainService;
    HowToPlayView howToPlayView;

    public ATPPlayerView(MainService mainService) {
        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("user-view");
        setWidthFull();
        setHeight("1700px");

        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabSetup();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(),
                createClickableDetailsParagraph("atptour.com/en/rankings", "ATP ranking"),
                rules,  generateRulesGrid());

        updateList();
        closeEditor();
    }

    private Grid generateRulesGrid(){
        ruleGrid.addClassNames("wta-player-rule-grid");
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

    private Paragraph createClickableDetailsParagraph(String website, String productDescription) {

        String pureWebsite = removeSpaces(website); // Remove spaces from website URL

        Anchor href = new Anchor("https://www." + pureWebsite, "here"); // Create hyperlink

        // Combine hyperlink and product description into a paragraph
        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see official details about " + productDescription));

        return paragraph;
    }

    private String removeSpaces(String value) {
        return value.replaceAll("\\s", "");
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
    form = new ATPPlayerForm( mainService.findAllAtpUsers());
    form.setWidth("25em");
    form.setHeight("400px");
    form.addListener(ATPPlayerForm.SaveEvent.class, this::saveATPPlayer);
    form.addListener(ATPPlayerForm.DeleteEvent.class, this::deleteATPPlayer);
    form.addListener(ATPPlayerForm.CloseEvent.class, e -> closeEditor());



}

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("fullname");
        grid.addColumn(user -> user.getUser().getNickname()).setHeader("User");
        grid.addColumn(atpPlayer -> atpPlayer.getDescription()).setHeader("Description");
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
        Icon setupIcon = new Icon(VaadinIcon.COG);
        setupIcon.setColor("black");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton, setupIcon);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveATPPlayer(ATPPlayerForm.SaveEvent event) {
        mainService.saveATPPlayer(event.getAtpPlayer());
        updateList();
        closeEditor();
    }

    private void deleteATPPlayer(ATPPlayerForm.DeleteEvent event) {
        try{
                        mainService.deleteATPPlayer(event.getAtpPlayer());
                   }catch (
                DataIntegrityViolationException e){
            e.printStackTrace();
            Notification notification = Notification
                    .show("This player is used in another view!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.MIDDLE);
        }
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
        ruleGrid.setItems(mainService.findAllHowToPlay(filterText.getValue()));
    }

}
