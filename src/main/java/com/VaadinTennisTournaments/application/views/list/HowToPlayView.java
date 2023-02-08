package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
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

    public HowToPlayView(MainService mainService) {

        this.mainService = mainService;
        addClassName("how-to-play-view");
        setSizeFull();
        configureTabs();
        add(configureTabs());
    }
    private Tabs configureTabs() {

        Tab generalRules = new Tab(VaadinIcon.BOOK.create(), new Span("General Rules"));
        Tab profile = new Tab(VaadinIcon.USER.create(), new Span("Users"));
        Tab atpWta = new Tab(VaadinIcon.SCALE.create(), new Span("ATP/WTA"));
        Tab results = new Tab(VaadinIcon.ARCHIVE.create(), new Span("Results"));
        Tab punctation = new Tab(VaadinIcon.ABACUS.create(), new Span("WTAPunctation"));
        Tab ranking = new Tab(VaadinIcon.CHART.create(), new Span("Ranking"));

        generalRules.add(generateNotEditableText("Generalne Zasady:" +
                        "\n\n" +
                "Zabawa polega na wybraniu zwycięzcy turnieju na możliwie najwcześniejszym etapie rozgrywek." +
                        "\n" +
                "W przypadku odpadnięcia kandydata przed wygraną, " +
                        "można dokonać kolejnego wyboru poprzez modyfikacje bieżącej predykcji o odpowiednie dane, " +
                "włącznie z wyborem nowego etapu turnieju, który określa czas jej wystąpienia." +
                        "\n\n" +
                        "Ilość uzyskanych punktów jest zależna od etapu na którym został wybrany zwycięzca oraz od poziomu rozgrywek. " +
                        "\n" +
                        "Im wcześniej wybrany zwycięzca oraz im wyższa ranga turnieju, tym więcej otrzymanych punktów." +
                        "\n\n" +
                        "Na koniec roku wybierane są  wyniki 10 turniejów z najwyższa liczba punktów, oddzielnie ATP/WTA, dla każdego uczestnika" +
                        "Na tej podstawie jest wyłaniany ranking najlepszych graczy."
                         ));

        profile.add(generateNotEditableText(
                "1. Nazwa oraz email użytkownika powinny być unikatowe, " +
                "\n"+
                "2. Jedna osoba nie moze dodac wiecej niz jednego uzytkownika" +
                "\n"+
                "3. Wybrane zainteresowania powinny być zbieżne z udziałem w predykcji, w poszczególnych tabelach"
                ));

        atpWta.add(generateNotEditableText(
                "1. Dla jednego wyboru można wybrać jedynie jednego zawodnika oraz jeden turniej" +
                "\n"+
                "2. Dodany zawodnik i turniej powinien być możliwy do identyfikacji przez wyszstkich uczestników" +
                "\n"+
                "3. Usuwaniem wyników oraz tworzeniem punktacji, po danym tuenieju, zajmuje się jedna, wyznaczona do tego zadania osoba"
        ));
        results.add(generateNotEditableText(
                        "1. Rezultaty powinny być dodawane po zakończonym turnieju przez osobę, która dodała dany turniej w swojej predykcji" +
                        "\n"+
                        "2. Dodany zawodnik i turniej, powinien być możliwy do identyfikacji przez wyszystkich uczestników" +
                        "\n"+
                        "3. Usuwaniem wyników oraz tworzeniem punktacji, w widoku punktajci, po danym tuenieju, zajmuje się jedna, wyznaczona do tego zadania osoba"
        ));

        punctation.add(generateNotEditableText(
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
                        "Punktacja = 20pkt * 3.0 = 60 pkt"
        ));
        ranking.add(generateNotEditableText(
                        "1. " +
                        "\n"+
                        "2. " +
                        "\n"+
                        "3. "
        ));

        generalRules.add(generateEditableText("Write your own General Rules"));
        profile.add(generateEditableText("Write your own User description"));
        atpWta.add(generateEditableText("Write your own ATP/WTA rule explanation"));
        results.add(generateEditableText("Write your own Results interpretation explanation"));
        punctation.add(generateEditableText("Write your own Results interpretation explanation"));
        ranking.add(generateEditableText("Write your own Results interpretation explanation"));

// Set the icon on top
        for (Tab tab : new Tab[] { generalRules, profile, atpWta, results, punctation, ranking }) {
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        }

        Tabs tabs = new Tabs(generalRules, profile, atpWta, results, punctation, ranking);

        tabs.setMaxWidth("100%");
        tabs.setAutoselect(true);
        return tabs;
    }

   private TextArea generateNotEditableText(String text){

        TextArea textArea = new TextArea();
        textArea.setWidth("400px");
        textArea.setMinHeight("200px");
        textArea.setMaxHeight("400px");

        textArea.setReadOnly(true);

        textArea.setValue(text);
        return textArea;

    }

    private TextArea generateEditableText(String text){

        TextArea textArea = new TextArea();
        textArea.setWidth("400px");
        textArea.setMinHeight("200px");
        textArea.setMaxHeight("600px");

        textArea.setPlaceholder(text);
        return textArea;
    }





}
