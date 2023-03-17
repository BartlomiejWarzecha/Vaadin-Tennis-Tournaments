package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.wta.WTATournament;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class WTATournamentForm extends FormLayout {
    private WTATournament wtaTournament;
    TextField tournament = new TextField("Tournament");
    ComboBox<Rank> rank = new ComboBox<>("Rank");
    TextArea description = new TextArea("Description");
    Binder<WTATournament> binder = new BeanValidationBinder<>(WTATournament.class);
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public WTATournamentForm(List<Rank> ranks) {
        addClassName("WTATournament-form");
        binder.bindInstanceFields(this);

        rank.setItems(ranks);
        rank.setItemLabelGenerator(Rank::getName);

        add(tournament,
                rank,
                description,
                createButtonsLayout());
    }
    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, wtaTournament)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }
    public void setWtaTournament(WTATournament wtaTournament) {
        this.wtaTournament = wtaTournament;
        binder.readBean(wtaTournament);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(wtaTournament);
            fireEvent(new SaveEvent(this, wtaTournament));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    public static abstract class WTATournamentFormEvent extends ComponentEvent<WTATournamentForm> {
        private WTATournament wtaTournament;

        protected WTATournamentFormEvent(WTATournamentForm source, WTATournament wtaTournament) {
            super(source, false);
            this.wtaTournament = wtaTournament;
        }

        public WTATournament getWtaTournament() {
            return wtaTournament;
        }
    }
    public static class SaveEvent extends WTATournamentFormEvent {
        SaveEvent(WTATournamentForm source, WTATournament wtaTournament) {
            super(source, wtaTournament);
        }
    }
    public static class DeleteEvent extends WTATournamentFormEvent {
        public DeleteEvent(WTATournamentForm source, WTATournament wtaTournament) {
            super(source, wtaTournament);
        }
    }
    public static class CloseEvent extends WTATournamentFormEvent {
        public CloseEvent(WTATournamentForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}