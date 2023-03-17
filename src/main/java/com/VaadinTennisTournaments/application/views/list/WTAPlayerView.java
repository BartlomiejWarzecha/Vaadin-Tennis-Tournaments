package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.wta.WTAPlayer;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
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

import static com.vaadin.flow.component.notification.Notification.show;

@Component
@Scope("prototype")
@Route(value = "WTAPlayer", layout = MainLayout.class)
@PageTitle("WTA Player | Vaadin Tennis Tournaments")
@PermitAll
public class WTAPlayerView extends VerticalLayout {
    Grid<WTAPlayer> grid = new Grid<>(WTAPlayer.class);
    Grid<HowToPlay> ruleGrid = new Grid<>(HowToPlay.class);

    TextField filterText = new TextField();
    WTAPlayerForm form;
    MainService mainService;
    HowToPlayView howToPlayView;
    public WTAPlayerView(MainService mainService) {
        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("WTA-Player-view");
        setHeight("1700px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabSetup();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(),
                getHrefParagraph("wtatennis.com/rankings/singles", "WTA ranking"),
                rules, generateRulesGrid());
        updateList();
        closeEditor();
    }
    private Grid generateRulesGrid(){
        ruleGrid.addClassNames("wta-player-rule-grid");
        ruleGrid.setSizeFull();
        ruleGrid.setColumns();
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getUser().getNickname()).setHeader("User");
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getSetupDescription()).setHeader("setup users rules:").setFlexGrow(1); // Set the flex grow value of this column to 1;
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
        form = new WTAPlayerForm(mainService.findAllWtaUsers());
        form.setWidth("25em");
        form.setHeight("400px");
        form.addListener(WTAPlayerForm.SaveEvent.class, this::saveWTAPlayer);
        form.addListener(WTAPlayerForm.DeleteEvent.class, this::deleteWTAPlayer);
        form.addListener(WTAPlayerForm.CloseEvent.class, A -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("fullname");
        grid.addColumn(user -> user.getUser().getNickname()).setHeader("User");
        grid.addColumn(wtaPlayer -> wtaPlayer.getDescription()).setHeader("Description");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editWTAPlayer(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Icon setupIcon = new Icon(VaadinIcon.COG);
        setupIcon.setColor("black");

        Button addUserButton = new Button("Add WTA Player");
        addUserButton.addClickListener(click -> addWTAPlayer());
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton, setupIcon);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveWTAPlayer(WTAPlayerForm.SaveEvent event) {
        mainService.saveWTAPlayer(event.getWTAPlayer());
        updateList();
        closeEditor();
    }

    private void deleteWTAPlayer(WTAPlayerForm.DeleteEvent event) {
                try{
                        mainService.deleteWTAPlayer(event.getWTAPlayer());
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

    public void editWTAPlayer(WTAPlayer WTAPlayer) {
        if (WTAPlayer == null) {
            closeEditor();
        } else {
            form.setWTAPlayer(WTAPlayer);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addWTAPlayer() {
        grid.asSingleSelect().clear();
        editWTAPlayer(new WTAPlayer());
    }

    private void closeEditor() {
        form.setWTAPlayer(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllWTAPlayers(filterText.getValue()));
        ruleGrid.setItems(mainService.findAllHowToPlay(filterText.getValue()));
    }

    private Paragraph getHrefParagraph(String hrefValue, String description) {
        String pureHrefValue = hrefValue.replaceAll("\\s", "");// value without spaces
        Anchor href = new Anchor("https://www." + pureHrefValue, "here");
        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see official details about " + description));
        return paragraph;
    }
}