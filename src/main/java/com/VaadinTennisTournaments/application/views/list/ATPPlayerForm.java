package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPlayer;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ATPPlayerForm extends FormLayout {
  private ATPPlayer atpPlayer;

  TextField fullname = new TextField("ATP Player FullName");
  TextArea description = new TextArea("Description");
  ComboBox<User> user= new ComboBox<>("");
  Binder<ATPPlayer> binder = new BeanValidationBinder<>(ATPPlayer.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public ATPPlayerForm(List<User> users) {
    addClassName("user-form");
    binder.bindInstanceFields(this);

    user.setItems(users);
    user.setItemLabelGenerator(User::getNickname);
    add(fullname,
            user,
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
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, atpPlayer)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setAtpPlayer(ATPPlayer atpPlayer) {
    this.atpPlayer = atpPlayer;
    binder.readBean(atpPlayer);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(atpPlayer);
      fireEvent(new SaveEvent(this, atpPlayer));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class ATPPlayerFormEvent extends ComponentEvent<ATPPlayerForm> {
    private ATPPlayer atpPlayer;

    protected ATPPlayerFormEvent(ATPPlayerForm source, ATPPlayer atpPlayer) {
      super(source, false);
      this.atpPlayer = atpPlayer;
    }

    public ATPPlayer getAtpPlayer() {
      return atpPlayer;
    }
  }

  public static class SaveEvent extends ATPPlayerFormEvent{
    SaveEvent(ATPPlayerForm source, ATPPlayer atpPlayer) {
      super(source, atpPlayer);
    }
  }

  public static class DeleteEvent extends ATPPlayerFormEvent{
    DeleteEvent(ATPPlayerForm source, ATPPlayer atpPlayer) {
      super(source, atpPlayer);
    }

  }

  public static class CloseEvent extends ATPPlayerFormEvent{
    CloseEvent(ATPPlayerForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}