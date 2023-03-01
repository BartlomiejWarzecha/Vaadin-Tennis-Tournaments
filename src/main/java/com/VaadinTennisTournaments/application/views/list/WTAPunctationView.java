package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAPunctation;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
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
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Scope("prototype")
@Route(value = "WTAPunctation", layout = MainLayout.class)
@PageTitle("WTA Punctation | Vaadin Tennis Tournaments")
@PermitAll
public class WTAPunctationView extends VerticalLayout {
    Grid<WTAPunctation> grid = new Grid<>(WTAPunctation.class);
    TextField filterText = new TextField();
    WTAPunctationForm form;
    MainService mainService;
    HowToPlayView howToPlayView;

    public WTAPunctationView(MainService mainService, HowToPlayView howToPlayView) {
        this.mainService = mainService;
        this.howToPlayView = howToPlayView;

        addClassName("puncation-view");
        setHeight("1300px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabAtpWta();
        HorizontalLayout rules = new HorizontalLayout(tab);

        add(getToolbar(), getContent(), getHrefParagraph("WTA Tennis", "ATP Tour"), rules);


        updateList();
        closeEditor();
    }
    private Paragraph getHrefParagraph(String value, String secondValue){

        String pureValue =  value.replaceAll("\\s", "");// value without spaces
        String pureSecondValue =  secondValue.replaceAll("\\s", "");// value without spaces

        Anchor firstHref = new Anchor("https://www."+pureValue+".com/scores",  value);
        Anchor secondHref = new Anchor("https://www."+pureSecondValue+".com/scores", secondValue);

        Paragraph paragraph = new Paragraph(new Text("See more information about different tournament results from official sites: "), firstHref, new Text(", "), secondHref);

        return paragraph;
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
    form = new WTAPunctationForm( mainService.findAllRanks(),
            mainService.findAllStages(), mainService.findAllUsers( "") , mainService.findAllWTA(""));
    form.setWidth("25em");
    form.setHeight("400px");
    form.addListener(WTAPunctationForm.SaveEvent.class, this::saveWTAPunctation);
    form.addListener(WTAPunctationForm.DeleteEvent.class, this::deleteWTAPunctation);
    form.addListener(WTAPunctationForm.CloseEvent.class, e -> closeEditor());
}
    private void configureGrid() {
        grid.addClassNames("punctation-grid");
        grid.setSizeFull();
        grid.setColumns( "points");
        grid.addColumn(WTAPunctation -> WTAPunctation.getUser().getNickname()).setHeader("User");
        grid.addColumn(WTAPunctation -> WTAPunctation.getStage().getName()).setHeader("Stage");
        grid.addColumn(WTAPunctation -> WTAPunctation.getWtaTournament()).setHeader("WTA Tournament");
        grid.addColumn(WTAPunctation -> WTAPunctation.getRank().getName()).setHeader("Rank");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editWTAPunctation(event.getValue()));
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Icon punctationIcon = new Icon(VaadinIcon.ABACUS);
        punctationIcon.setColor("black");
        Button addPredicionButton = new Button("Add user result");
        addPredicionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addPredicionButton.addClickListener(click -> addPunctation());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton, punctationIcon);
        toolbar.addClassName("toolbar-WTA-Punctation");
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
    private void saveWTAPunctation(WTAPunctationForm.SaveEvent event) {
        mainService.saveWTAPunctation(event.getPunctation());
        updateList();
        closeEditor();
    }

    private void deleteWTAPunctation(WTAPunctationForm.DeleteEvent event) {
        mainService.deleteWTAPunctation(event.getPunctation());

        updateList();
        closeEditor();
    }

    public void editWTAPunctation(WTAPunctation WTAPunctation) {
        if (WTAPunctation == null) {
            closeEditor();
        } else {
            form.setWTAPunctation(WTAPunctation);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addPunctation() {
        grid.asSingleSelect().clear();
        editWTAPunctation(new WTAPunctation());
    }

    private void closeEditor() {
        form.setWTAPunctation(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllWTAPunctation(filterText.getValue()));
    }
}
