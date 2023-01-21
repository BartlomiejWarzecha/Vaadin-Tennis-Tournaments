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
        Tab results = new Tab(VaadinIcon.BAR_CHART.create(), new Span("Results"));

        generalRules.add(generateNotEditableText("General Rules" +
                "\n"+
                ""));

        profile.add(generateNotEditableText("User description"));
        atpWta.add(generateNotEditableText("ATP/WTA rule explanation"));
        results.add(generateNotEditableText("Results interpretation explanation"));

        generalRules.add(generateEditableText("Write your own General Rules"));
        profile.add(generateEditableText("Write your own User description"));
        atpWta.add(generateEditableText("Write your own ATP/WTA rule explanation"));
        results.add(generateEditableText("Write your own Results interpretation explanation"));

// Set the icon on top
        for (Tab tab : new Tab[] { generalRules, profile, atpWta, results }) {
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        }

        Tabs tabs = new Tabs(generalRules, profile, atpWta, results);

        tabs.setMaxWidth("100%");
        tabs.setAutoselect(true);
        return tabs;
    }

   private TextArea generateNotEditableText(String text){

        TextArea textArea = new TextArea();
        textArea.setWidth("400px");
        textArea.setMinHeight("200px");
        textArea.setMaxHeight("600px");

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
