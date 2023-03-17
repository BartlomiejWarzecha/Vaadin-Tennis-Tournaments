package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value = "How-To-Play", layout = MainLayout.class)
@PageTitle("How to play | Vaadin Tennis Tournaments")
@PermitAll
public class HowToPlayView extends VerticalLayout {
    MainService mainService;
    Grid<HowToPlay> grid = new Grid<>(HowToPlay.class);
    HowToPlayForm form;

    TextField filterText = new TextField();

    public HowToPlayView(MainService mainService) {

        this.mainService = mainService;
        addClassName("how-to-play-view");
        setSizeFull();
        configureGrid();
        configureForm();
        HorizontalLayout systemRules = new HorizontalLayout(configureTabs());
        add(systemRules, getToolbar(), getContent());
        updateList();
        closeEditor();
    }
    private Tabs configureTabs() {

        Tab generalRules = getTabGeneralRules();
        Tab prediction = getTabPrediction();
        Tab profile = getTabProfile();
        Tab results = getTabResults();
        Tab punctation = getTabPunctation();
        Tab ranking = getTabRanking();
        Tab setup = getTabSetup();

        for (Tab tab : new Tab[] { generalRules, profile, prediction, results, punctation, ranking, setup }) {
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);

        }
        Tabs tabs = new Tabs(generalRules, profile, setup, prediction, results, punctation, ranking);

        tabs.setMaxWidth("100%");
        tabs.setAutoselect(true);
        return tabs;
    }

public Tab getTabGeneralRules() {
        Tab tab = new Tab(VaadinIcon.BOOK.create(), new Span("general rules:"));

        tab.add(generateNotEditableText("Generalne Zasady:\n\n" +
                "Zabawa polega na wybraniu zwycięzcy turnieju na możliwie najwcześniejszym etapie rozgrywek.\n\n" +
                "W przypadku odpadnięcia kandydata przed wygraną, można dokonać kolejnego wyboru poprzez modyfikacje bieżącej predykcji o odpowiednie dane, włącznie z wyborem nowego etapu turnieju, który określa czas jej wystąpienia.\n\n" +
                "Ilość uzyskanych punktów jest zależna od etapu na którym został wybrany zwycięzca oraz od poziomu rozgrywek. Im wcześniej wybrany zwycięzca oraz im wyższa ranga turnieju, tym więcej otrzymanych punktów.\n\n" +
                "Na koniec roku wybierane są wyniki 10 turniejów z najwyższą liczbą punktów, oddzielnie ATP/WTA, dla każdego uczestnika. Na tej podstawie jest wyłaniany ranking najlepszych graczy."
        ));

        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;

    }

    public Tab getTabProfile() {

        Tab tab = new Tab(VaadinIcon.USER.create(), new Span("profile rules"));

        tab.add(generateNotEditableText(
                "1. Nazwa oraz email użytkownika, powinny być unikatowe " +
                        "\n" +
                        "2. Jedna osoba nie moze dodac wiecej niz jednego uzytkownika" +
                        "\n" +
                        "3. Wybrane zainteresowania powinny być zbieżne z udziałem w predykcji"
        ));

        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;
    }

    public Tab getTabPrediction() {


        Tab tab = new Tab(VaadinIcon.SCALE.create(), new Span("prediction rules:"));

        tab.add(generateNotEditableText(
                "1. Dla jednego wyboru można wybrać jedynie jednego zawodnika oraz jeden turniej" +
                        "\n" +
                        "2. Dodany zawodnik i turniej powinien być możliwy do identyfikacji przez wyszstkich uczestników" +
                        "\n" +
                        "3. Usuwaniem wyników oraz tworzeniem punktacji, po danym tuenieju, zajmuje się jedna, wyznaczona do tego zadania osoba"
        ));

        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;
    }

    public Tab getTabResults() {

        Tab tab = new Tab(VaadinIcon.SCALE.create(), new Span("results rules:"));

        tab.add(generateNotEditableText(
                "1. Rezultaty powinny być dodawane po zakończonym turnieju przez osobę, która dodała dany turniej w swojej predykcji" +
                        "\n" +
                        "2. Osoba, która doda dany rezultat, powinna dodać również punktacje dla wszystkich predykcji " +
                        "\n" +
                        "3. Dodajemy rezultaty tylko tych turniejów, które posiadają jakąkolwiek predykcje"
        ));

        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;
    }

    public Tab getTabPunctation() {

        Tab  tab= new Tab(VaadinIcon.ABACUS.create(), new Span("punctation rules:"));
        tab.add(generateNotEditableText(
                        "Wzór: etap wybrania zwycięzcyy * poziom rozgrywek"+
                        "\n\n"+
                        "Etap: "+
                        "\n"+
                        "1/256 - 50pkt " +
                        "\n"+
                        "1/128 - 45pkt " +
                        "\n"+
                        "1/64 - 35pkt " +
                        "\n"+
                        "1/32 - 30pkt " +
                        "\n"+
                        "1/16 - 25pkt " +
                        "\n"+
                        "1/8 - 20pkt " +
                        "\n"+
                        "QF - 15pkt " +
                        "\n"+
                        "SF - 10pkt " +
                        "\n"+
                        "F - 5pkt " +
                        "\n\n" +
                        "Poziom: " +
                        "\n" +
                        "Chellenger - 0.1" +
                        "\n" +
                        "100 - 0.1" +
                        "\n" +
                        "250 - 0.25" +
                        "\n" +
                        "500 - 0.5" +
                        "\n" +
                        "1000 - 1.0" +
                        "\n" +
                        "Grand Slam - 3.0" +
                        "\n\n" +
                        "Przykład:" +
                        "\n" +
                        "Zwycięzaca wybrany na etapie 1/8 Grand Slam" +
                        "\n" +
                        "Punktacja = 20pkt * 3.0 = 60 pkt" ));
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;
    }

    public Tab getTabRanking() {
        Tab tab = new Tab(VaadinIcon.CHART.create(), new Span("ranking rules:"));
        tab.add(generateNotEditableText(
                "1.  Ranking powinien obejmować maksymalnie 10 turniejów rozgrywanych w roku" +
                        "\n"+
                "2. Podsumowanie rankingu powinno odbywać się kwartalnie oraz rocznie, przez wyznaczoną do tego osobę" +
                        "\n"+
                        "3. W Jedna osoba może mieć wiele wyników rankingowych, " +
                        "szczegóły poszczególnych wyników, są do ustalenia przez użytkowników "
        ));
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;
    }

    public Tab getTabSetup() {

        Tab tab = new Tab(VaadinIcon.COGS.create(), new Span("setup rules:"));

        tab.add(generateNotEditableText(
                "1. Dodawane dane powinny być unikatowe " +
                        "\n"+
                        "2. Zawodnicy i turnieje powinny być łatwe do odnalezienia, po wprowadzonych nazwach, na oficjalnych stronach organizacji " +
                        "\n"+
                        "3. Usuwanie zawodików i turniejów, które są obecne w innych panelach, nie jest możliwe."
        ));

        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;
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

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter data...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Icon rulesIcon = new Icon(VaadinIcon.BOOK);
        rulesIcon.setColor("black");

        Button addRulesButton = new Button("Create your own rules");
        addRulesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        addRulesButton.addClickListener(click -> addHowToPlay());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addRulesButton, rulesIcon);
        toolbar.addClassName("toolbar-How-To-Play");
        return toolbar;
    }

    private void configureForm() {
        form = new HowToPlayForm(mainService.findAllUsers(""));
        form.setWidth("25em");
        form.setHeight("400px");
        form.addListener(HowToPlayForm.SaveEvent.class, this::saveHowToPlay);
        form.addListener(HowToPlayForm.DeleteEvent.class, this::deleteHowToPlay);
        form.addListener(HowToPlayForm.CloseEvent.class, e -> closeEditor());
    }


    private void configureGrid() {
        grid.addClassNames("howToPlay-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(HowToPlay -> HowToPlay.getUser().getNickname()).setHeader("User").setFlexGrow(1);
        grid.addColumn(HowToPlay -> HowToPlay.getGeneralRulesDescription()).setHeader("General Rules").setFlexGrow(1);
        grid.addColumn(HowToPlay -> HowToPlay.getUsersDescription()).setHeader("Profiles").setFlexGrow(1);
        grid.addColumn(HowToPlay -> HowToPlay.getSetupDescription()).setHeader("Setup").setFlexGrow(1);
        grid.addColumn(HowToPlay -> HowToPlay.getPredictionDescription()).setHeader("Prediction").setFlexGrow(1);
        grid.addColumn(HowToPlay -> HowToPlay.getPunctationDescription()).setHeader("Punctation").setFlexGrow(1);
        grid.addColumn(HowToPlay -> HowToPlay.getResultsDescription()).setHeader("Results").setFlexGrow(1);
        grid.addColumn(HowToPlay -> HowToPlay.getRankingDescription()).setHeader("Ranking").setFlexGrow(1);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editHowToPlay(event.getValue()));

    }


    private void saveHowToPlay(HowToPlayForm.SaveEvent event) {
        mainService.saveHowToPlay(event.getHowToPlay());
        updateList();
        closeEditor();
    }

    private void deleteHowToPlay(HowToPlayForm.DeleteEvent event) {
        mainService.deleteHowToPlay(event.getHowToPlay());
        updateList();
        closeEditor();
    }

    public void editHowToPlay(HowToPlay howToPlay) {
        if (howToPlay == null) {
            closeEditor();
        } else {
            form.setHowToPlay(howToPlay);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addHowToPlay() {
        grid.asSingleSelect().clear();
        editHowToPlay(new HowToPlay());
    }

    private void closeEditor() {
        form.setHowToPlay(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(mainService.findAllHowToPlay(filterText.getValue()));
    }
    private TextArea generateNotEditableText(String text) {
        TextArea textArea = new TextArea();
        textArea.setWidth("400px");
        textArea.setMinHeight("200px");
        textArea.setMaxHeight("400px");
        textArea.setReadOnly(true);
        textArea.setValue(text);
        return textArea;
    }

}
