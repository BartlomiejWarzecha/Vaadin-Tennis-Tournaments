package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATP;
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
@Route(value = "ATPPrediction", layout = MainLayout.class)
@PageTitle("ATP Prediction | Vaadin Tennis Tournaments")
@PermitAll
public class ATPPredictionView extends VerticalLayout {
    Grid<ATP> grid = new Grid<>(ATP.class);
    TextField filterText = new TextField();
    ATPPredictionForm form;
    MainService mainService;

    public ATPPredictionView(MainService mainService) {
        this.mainService = mainService;
        addClassName("atp-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent(), getHrefParagraph("ATP Tour", "ATP Tour"));
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
    form = new ATPPredictionForm(mainService.findAllStages(), mainService.findAllUsers(""));
    form.setWidth("25em");
    form.setHeight("40em");
    form.addListener(ATPPredictionForm.SaveEvent.class, this::saveATP);
    form.addListener(ATPPredictionForm.DeleteEvent.class, this::deleteATP);
    form.addListener(ATPPredictionForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("atp-grid");
        grid.setSizeFull();
        grid.setColumns("player", "atpTournament" );
        grid.addColumn(atp-> atp.getUser().getNickname()).setHeader("User");
        grid.addColumn(atp-> atp.getStage().getName()).setHeader("Stage");
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
        closeEditor();
    }

    private void deleteATP(ATPPredictionForm.DeleteEvent event) {
        mainService.deleteATP(event.getATP());

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
    }

    private Paragraph getHrefParagraph(String hrefValue , String description){

        String pureHrefValue = hrefValue.replaceAll("\\s", "");// value without spaces

        Anchor href = new Anchor("https://www."+pureHrefValue+".com/", "here");

        Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see official details about " + description));

        return paragraph;
    }

}
