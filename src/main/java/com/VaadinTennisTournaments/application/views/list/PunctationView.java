package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.Punctation.Punctation;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value = "Punctation", layout = MainLayout.class)
@PageTitle("Punctation | Vaadin Tennis Tournaments")
@PermitAll
public class PunctationView extends VerticalLayout {
    Grid<Punctation> grid = new Grid<>(Punctation.class);
    TextField filterText = new TextField();
    PunctationForm form;
    MainService mainService;

    public PunctationView(MainService mainService) {
        this.mainService = mainService;
        addClassName("puncation-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent(), getHrefScoreParagraph("WTA Tennis", "ATP Tour"));
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
    form = new PunctationForm(mainService.findAllInterests(), mainService.findAllRanks(), mainService.findAllStages());
    form.setWidth("25em");
    form.setHeight("40em");
    form.addListener(PunctationForm.SaveEvent.class, this::savePunctation);
    form.addListener(PunctationForm.DeleteEvent.class, this::deletePunctation);
    form.addListener(PunctationForm.CloseEvent.class, e -> closeEditor());
}
    private void configureGrid() {
        grid.addClassNames("punctation-grid");
        grid.setSizeFull();

        grid.setColumns( "points", "nickname", "tournament");
        grid.addColumn(punctation-> punctation.getInterest().getName()).setHeader("Type");
        grid.addColumn(punctation-> punctation.getRank().getName()).setHeader("Rank");
        grid.addColumn(punctation-> punctation.getStage().getName()).setHeader("Stage");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editPunctation(event.getValue()));
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by any data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPredicionButton = new Button("Add Punctation for user");
        addPredicionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addPredicionButton.addClickListener(click -> addPunctation());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPredicionButton);
        toolbar.addClassName("toolbar-ATP");
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
    private void savePunctation(PunctationForm.SaveEvent event) {
        mainService.savePunctation(event.getPunctation());
        updateList();
        closeEditor();
    }

    private void deletePunctation(PunctationForm.DeleteEvent event) {
        mainService.deletePunctation(event.getPunctation());

        updateList();
        closeEditor();
    }

    public void editPunctation(Punctation punctation) {
        if (punctation == null) {
            closeEditor();
        } else {
            form.setPunctation(punctation);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addPunctation() {
        grid.asSingleSelect().clear();
        editPunctation(new Punctation());
    }

    private void closeEditor() {
        form.setPunctation(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllPunctation(filterText.getValue()));
    }
}
