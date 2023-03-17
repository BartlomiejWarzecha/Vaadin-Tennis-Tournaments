package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.user.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class HowToPlayForm extends FormLayout {
  private HowToPlay howToPlay;
  ComboBox<User> user = new ComboBox<User>("User");
  TextArea generalRulesDescription = new TextArea("General Rules");
  TextArea usersDescription = new TextArea("Profiles");
  TextArea setupDescription = new TextArea("Setup");
  TextArea predictionDescription = new TextArea("Prediction");
  TextArea punctationDescription = new TextArea("Punctation");
  TextArea resultsDescription = new TextArea("Results");
  TextArea rankingDescription = new TextArea("Ranking");
  Binder<HowToPlay> binder = new BeanValidationBinder<>(HowToPlay.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public HowToPlayForm(List<User> users) {
    addClassName("howToPlay-form");
    binder.bindInstanceFields(this);

    user.setItems(users);
    user.setItemLabelGenerator(User::getNickname);

    add(
            user, generalRulesDescription, usersDescription, setupDescription,
            predictionDescription, punctationDescription, resultsDescription, rankingDescription,
           createButtonsLayout());
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, howToPlay)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setHowToPlay(HowToPlay howToPlay) {
    this.howToPlay = howToPlay;
    binder.readBean(howToPlay);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(howToPlay);
      fireEvent(new SaveEvent(this, howToPlay));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  public static abstract class HowToPlayFormEvent extends ComponentEvent<HowToPlayForm> {
    private HowToPlay howToPlay;

    protected HowToPlayFormEvent(HowToPlayForm source, HowToPlay howToPlay) {
      super(source, false);
      this.howToPlay = howToPlay;
    }

    public HowToPlay getHowToPlay() {
      return howToPlay;
    }
  }

    public static class SaveEvent extends HowToPlayFormEvent {
      SaveEvent(HowToPlayForm source, HowToPlay howToPlay) {
        super(source, howToPlay);
      }
    }

    public static class DeleteEvent extends HowToPlayFormEvent{
      DeleteEvent(HowToPlayForm source, HowToPlay howToPlay) {
        super(source, howToPlay);
      }

    }

    public static class CloseEvent extends HowToPlayFormEvent{
      CloseEvent(HowToPlayForm source) {
        super(source, null);
      }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
      return getEventBus().addListener(eventType, listener);
    }
  }
