package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATP;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPunctation;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPunctation;
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
@Route(value = "ATPPunctation", layout = MainLayout.class)
@PageTitle("ATP Punctation | Vaadin Tennis Tournaments")
@PermitAll
public class ATPPunctationView extends VerticalLayout {
    Grid<ATPPunctation  > grid = new Grid<>(ATPPunctation.class);
    TextField filterText = new TextField();
    ATPPunctationForm form;
    MainService mainService;
    HowToPlayView howToPlayView;

    public ATPPunctationView(MainService mainService) {
        this.mainService = mainService;
        this.howToPlayView = new HowToPlayView(mainService);

        addClassName("atp-punctation-view");
        setHeight("1300px");
        setWidthFull();
        configureGrid();
        configureForm();

        Tab tab = howToPlayView.getTabPunctation();
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
    form = new ATPPunctationForm(mainService.findAllRanks(), mainService.findAllStages(),
    mainService.findAllUsers(""), mainService.findAllATP(""));
    form.setWidth("25em");
    form.setHeight("400px");
    form.addListener(ATPPunctationForm.SaveEvent.class, this::saveATPPunctation);
    form.addListener(ATPPunctationForm.DeleteEvent.class, this::deleteATPPunctation);
    form.addListener(ATPPunctationForm.CloseEvent.class, e -> closeEditor());
}
    private void configureGrid() {
        grid.addClassNames("punctation-grid");
        grid.setSizeFull();

        grid.setColumns( "points");
        grid.addColumn(ATPPunctation -> ATPPunctation.getUser().getNickname()).setHeader("User");
        grid.addColumn(ATPPunctation -> ATPPunctation.getStage().getName()).setHeader("Stage");
        grid.addColumn(ATPPunctation -> ATPPunctation.getAtpTournament()).setHeader("ATP Tournament");
        grid.addColumn(ATPPunctation -> ATPPunctation.getRank().getName()).setHeader("Rank");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editATPPunctation(event.getValue()));
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
        addPredicionButton.addClickListener(click -> addATPPunctation());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton, punctationIcon);
        toolbar.addClassName("toolbar-ATP-Punctation");
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
    private void saveATPPunctation(ATPPunctationForm.SaveEvent event) {
        mainService.saveATPPunctation(event.getATPPunctation());
        updateList();
        closeEditor();
    }

    private void deleteATPPunctation(ATPPunctationForm.DeleteEvent event) {
        mainService.deleteATPPunctation(event.getATPPunctation());

        updateList();
        closeEditor();
    }

    public void editATPPunctation(ATPPunctation atpPunctation) {
        if (atpPunctation == null) {
            closeEditor();
        } else {
            form.setATPPunctation(atpPunctation);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addATPPunctation() {
        grid.asSingleSelect().clear();
        editATPPunctation(new ATPPunctation());
    }

    private void closeEditor() {
        form.setATPPunctation(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllATPPunctation(filterText.getValue()));
    }
}
