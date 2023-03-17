package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.atp.ATP;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value = "ATPPrediction", layout = MainLayout.class)
@PageTitle("ATP Prediction | Vaadin Tennis Tournaments")
@PermitAll
public class ATPPredictionView extends VerticalLayout {
    Grid<ATP> grid = new Grid<>(ATP.class);
    TextField filterText = new TextField();
    Grid<HowToPlay> ruleGrid = new Grid<>(HowToPlay.class);

    ATPPredictionForm form;
    MainService mainService;
    HowToPlayView howToPlayView;

    public ATPPredictionView(MainService mainService, HowToPlayView howToPlayView) {
        this.mainService = mainService;
        this.howToPlayView = howToPlayView;

        addClassName("atp-prediction--view");
        setHeight("1700px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabPrediction();
        HorizontalLayout rules = new HorizontalLayout(tab);


        add(getToolbar(), getContent(),
                getHrefParagraph("ATP Tour", "ATP Tour"), rules,
                generateRulesGrid() );
        updateList();
        closeEditor();
    }
    private Grid generateRulesGrid() {
        ruleGrid.addClassNames("rule-grid");
        ruleGrid.setSizeFull();
        ruleGrid.setColumns();
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getUser().getNickname()).setHeader("User");
        ruleGrid.addColumn(HowToPlay -> HowToPlay.getPredictionDescription()).setHeader("prediction users rules").setFlexGrow(1); // Set the flex grow value of this column to 1;
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
    form = new ATPPredictionForm(mainService.findAllStages(),
            mainService.findAllAtpUsers(),
            mainService.findAllATPTournaments(""),
            mainService.findAllATPPlayers(""));
    form.setWidth("25em");
    form.setHeight("400px");
    form.addListener(ATPPredictionForm.SaveEvent.class, this::saveATP);
    form.addListener(ATPPredictionForm.DeleteEvent.class, this::deleteATP);
    form.addListener(ATPPredictionForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("atp-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(atp-> atp.getUser().getNickname()).setHeader("User");
        grid.addColumn(atp-> atp.getPlayer().getFullname()).setHeader("Player");
        grid.addColumn(atp-> atp.getAtpTournament().getTournament()).setHeader("Tournament");
        grid.addColumn(atp-> atp.getStage().getName()).setHeader("Stage");
        grid.addColumn(atp-> atp.getPlayer().getDescription()).setHeader("Player Description");
        grid.addColumn(atp-> atp.getAtpTournament().getRank().getName()).setHeader("Tournament Rank");
        grid.addColumn(atp-> atp.getAtpTournament().getDescription()).setHeader("Tournament Description");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editATP(event.getValue()));
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
        addPredicionButton.addClickListener(click -> addATP());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton, atpWtaIcon);
        toolbar.addClassName("toolbar-ATP-Prediction");
        return toolbar;
    }

    private void saveATP(ATPPredictionForm.SaveEvent event) {
        mainService.saveATP(event.getATP());
        updateList();
        generateRulesGrid();
        closeEditor();
    }

    private void deleteATP(ATPPredictionForm.DeleteEvent event) {
        try {
            mainService.deleteATP(event.getATP());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            Notification notification = Notification.show("This prediction is used in punctation view, delete punctation first");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.MIDDLE);
        }
        updateList();
        closeEditor();
    }

    public void editATP(ATP atp) {
        if (atp == null) {
            closeEditor();
        } else {
            form.setATP(atp);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addATP() {
        grid.asSingleSelect().clear();
        editATP(new ATP());
    }

    private void closeEditor() {
        form.setATP(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllATP(filterText.getValue()));
        ruleGrid.setItems(mainService.findAllHowToPlay(filterText.getValue()));
    }

    private Paragraph getHrefParagraph(String hrefValue , String description){

        String pureHrefValue = hrefValue.replaceAll("\\s", "");// value without spaces

        Anchor href = new Anchor("https://www."+pureHrefValue+".com/", "here");

        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see official details about " + description));

        return paragraph;
    }

}
