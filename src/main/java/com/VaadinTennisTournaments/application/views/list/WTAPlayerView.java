package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPlayer;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAPlayer;
import com.VaadinTennisTournaments.application.data.repository.UserRepository;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;

@Component
@Scope("prototype")
@Route(value = "WTAPlayer", layout = MainLayout.class)
@PageTitle("WTA Player | Vaadin Tennis Tournaments")
@PermitAll
public class WTAPlayerView extends VerticalLayout {
    Grid<WTAPlayer> grid = new Grid<>(WTAPlayer.class);
    TextField filterText = new TextField();
    WTAPlayerForm form;
    MainService mainService;
    HowToPlayView howToPlayView;
    public WTAPlayerView(MainService mainService) {
        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("WTA-Player-view");
        setHeight("1300px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabAtpWta();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(),
                getHrefParagraph("wtatennis.com/rankings/singles", "WTA ranking"),
                rules);
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

        Button addUserButton = new Button("Add WTA Player");
        addUserButton.addClickListener(click -> addWTAPlayer());
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveWTAPlayer(WTAPlayerForm.SaveEvent event) {
        mainService.saveWTAPlayer(event.getWTAPlayer());
        updateList();
        closeEditor();
    }

    private void deleteWTAPlayer(WTAPlayerForm.DeleteEvent event) {
        mainService.deleteWTAPlayer(event.getWTAPlayer());
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
    }

    private Paragraph getHrefParagraph(String hrefValue, String description) {
        String pureHrefValue = hrefValue.replaceAll("\\s", "");// value without spaces
        Anchor href = new Anchor("https://www." + pureHrefValue, "here");
        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see official details about " + description));
        return paragraph;
    }
}