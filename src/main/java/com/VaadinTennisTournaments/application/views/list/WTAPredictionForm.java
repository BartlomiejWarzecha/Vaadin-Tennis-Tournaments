package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class WTAPredictionForm extends FormLayout {
  private WTA wta;

  TextField wtaTournament = new TextField("Wta Tournament");
  TextField player  = new TextField("Player");

  ComboBox<User> user = new ComboBox<>("User");
  ComboBox<Stage> stage = new ComboBox<>("Stage");

  Binder<WTA> binder = new BeanValidationBinder<>(WTA.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public WTAPredictionForm(List<Stage> stages, List<User> users) {
    addClassName("wta-form");
    binder.bindInstanceFields(this);

    stage.setItems(stages);
    stage.setItemLabelGenerator(Stage::getName);
    user.setItems(users);
    user.setItemLabelGenerator(User::getNickname);

    add(
            player,
            wtaTournament,
            user,
            stage,
        createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, wta)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setWTA(WTA wta) {
    this.wta = wta;
    binder.readBean(wta);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(wta);
      fireEvent(new SaveEvent(this, wta));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  public static abstract class WTAFormEvent extends ComponentEvent<WTAPredictionForm> {
    private WTA wta;

    protected WTAFormEvent(WTAPredictionForm source, WTA wta) {
      super(source, false);
      this.wta = wta;
    }

    public WTA getWTA() {
      return wta;
    }
  }

  public static class SaveEvent extends WTAFormEvent {
    SaveEvent(WTAPredictionForm source, WTA wta) {
      super(source, wta);
    }
  }

  public static class DeleteEvent extends WTAFormEvent {
    DeleteEvent(WTAPredictionForm source, WTA wta) {
      super(source, wta);
    }

  }

  public static class CloseEvent extends WTAFormEvent {
    CloseEvent(WTAPredictionForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}