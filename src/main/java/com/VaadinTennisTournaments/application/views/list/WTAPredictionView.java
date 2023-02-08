package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
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
@Route(value = "WTAPrediction", layout = MainLayout.class)
@PageTitle("WTA Prediction | Vaadin Tennis Tournaments")
@PermitAll
public class WTAPredictionView extends VerticalLayout {
    Grid<WTA> grid = new Grid<>(WTA.class);
    TextField filterText = new TextField();
    WTAPredictionForm form;
    MainService mainService;

    public WTAPredictionView(MainService mainService) {
        this.mainService = mainService;
        addClassName("wta-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent(), getHrefParagraph("wtatennis", "WTA Tour"));
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
    form = new WTAPredictionForm(mainService.findAllStages(), mainService.findAllUsers(""));
    form.setWidth("25em");
    form.setHeight("45em");
    form.addListener(WTAPredictionForm.SaveEvent.class, this::saveWTA);
    form.addListener(WTAPredictionForm.DeleteEvent.class, this::deleteWTA);
    form.addListener(WTAPredictionForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("wta-grid");
        grid.setSizeFull();
        grid.setColumns( "player", "wtaTournament" );
        grid.addColumn(wta-> wta.getUser().getNickname()).setHeader("User");
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
        Icon atpWtaIcon = new Icon(VaadinIcon.SCALE);
        atpWtaIcon.setColor("black");
        Button addPredicionButton = new Button("Add prediction");
        addPredicionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addPredicionButton.addClickListener(click -> addWTA());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton, atpWtaIcon);
        toolbar.addClassName("toolbar-WTA-Prediction");
        return toolbar;
    }
    private Paragraph getHrefParagraph(String hrefValue , String description){

        String pureHrefValue = hrefValue.replaceAll("\\s", "");// value without spaces

        Anchor href = new Anchor("https://www."+pureHrefValue+".com/", "here");

        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see official details about " + description));

        return paragraph;
    }

    private void saveWTA(WTAPredictionForm.SaveEvent event) {
        mainService.saveWTA(event.getWTA());
        updateList();
        closeEditor();
    }

    private void deleteWTA(WTAPredictionForm.DeleteEvent event) {
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
