package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPTournament;
import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
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

public class ATPTournamentForm extends FormLayout {
    private ATPTournament atpTournament;
    TextField tournament = new TextField("Tournament");
    ComboBox<Rank> rank = new ComboBox<>("Rank");

    TextArea description = new TextArea("Description");
    Binder<ATPTournament> binder = new BeanValidationBinder<>(ATPTournament.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ATPTournamentForm(List<Rank> ranks) {
        addClassName("ATPTournament-form");
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
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, atpTournament)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setAtpTournament(ATPTournament atpTournament) {
        this.atpTournament = atpTournament;
        binder.readBean(atpTournament);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(atpTournament);
            fireEvent(new SaveEvent(this, atpTournament));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public abstract static class ATPTournamentFormEvent extends ComponentEvent<ATPTournamentForm> {
        private ATPTournament atpTournament;

        protected ATPTournamentFormEvent(ATPTournamentForm source, ATPTournament atpTournament) {
            super(source, false);
            this.atpTournament = atpTournament;
        }

        public ATPTournament getAtpTournament() {
            return atpTournament;
        }
    }

    public static class SaveEvent extends ATPTournamentFormEvent {
        SaveEvent(ATPTournamentForm source, ATPTournament atpTournament) {
            super(source, atpTournament);
        }
    }

    public static class DeleteEvent extends ATPTournamentFormEvent {
        DeleteEvent(ATPTournamentForm source, ATPTournament atpTournament) {
            super(source, atpTournament);
        }
    }

    public static class CloseEvent extends ATPTournamentFormEvent {
        CloseEvent(ATPTournamentForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}