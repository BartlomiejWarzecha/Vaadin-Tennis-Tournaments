package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.user.User;
import com.VaadinTennisTournaments.application.data.entity.wta.WTAPlayer;
import com.vaadin.flow.component.*;
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

public class WTAPlayerForm extends FormLayout {
    private WTAPlayer WTAPlayer;

    TextField fullname = new TextField("WTA Player FullName");
    TextArea description = new TextArea("Description");
    ComboBox<User> user = new ComboBox<>("");
    Binder<WTAPlayer> binder = new BeanValidationBinder<>(WTAPlayer.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public WTAPlayerForm(List<User> users) {
        addClassName("user-form");
        binder.bindInstanceFields(this);

        user.setItems(users);
        user.setItemLabelGenerator(User::getNickname);
        add(fullname, user, description, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, WTAPlayer)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setWTAPlayer(WTAPlayer WTAPlayer) {
        this.WTAPlayer = WTAPlayer;
        binder.readBean(WTAPlayer);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(WTAPlayer);
            fireEvent(new SaveEvent(this, WTAPlayer));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class WTAPlayerFormEvent extends ComponentEvent<WTAPlayerForm> {
        private WTAPlayer WTAPlayer;

        protected WTAPlayerFormEvent(WTAPlayerForm source, WTAPlayer WTAPlayer) {
            super(source, false);
            this.WTAPlayer = WTAPlayer;
        }

        public WTAPlayer getWTAPlayer() {
            return WTAPlayer;
        }
    }

    public static class SaveEvent extends WTAPlayerFormEvent {
        SaveEvent(WTAPlayerForm source, WTAPlayer WTAPlayer) {
            super(source, WTAPlayer);
        }
    }

    public static class DeleteEvent extends WTAPlayerFormEvent {
        DeleteEvent(WTAPlayerForm source, WTAPlayer WTAPlayer) {
            super(source, WTAPlayer);
        }
    }

    public static class CloseEvent extends WTAPlayerFormEvent {
        CloseEvent(WTAPlayerForm source) {
            super(source, null);
        }
    }


        public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                      ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
    }

}
