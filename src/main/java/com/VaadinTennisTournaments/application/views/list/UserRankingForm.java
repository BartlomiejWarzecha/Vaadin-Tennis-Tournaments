package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.entity.User.UserRanking;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class UserRankingForm extends FormLayout {
  private UserRanking userRanking;

  TextField points = new TextField("Points");

  TextField tournamentsNumber = new TextField("Tournaments Number");
  ComboBox<Interests> interest = new ComboBox<>("Interests");

  ComboBox<User> user = new ComboBox<>("User");
  Binder<UserRanking> binder = new BeanValidationBinder<>(UserRanking.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public UserRankingForm(List<Interests> interests, List<User> users) {
    addClassName("user-ranking-form");
    binder.bindInstanceFields(this);

    interest.setItems(interests);
    interest.setItemLabelGenerator(Interests::getName);

    user.setItems(users);
    user.setItemLabelGenerator(User::getNickname);

    add(
            tournamentsNumber,
            points,
             user,
            interest,
        createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, userRanking)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setUserRanking(UserRanking userRanking) {
    this.userRanking = userRanking;
    binder.readBean(userRanking);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(userRanking);
      fireEvent(new SaveEvent(this, userRanking));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class UserFormEvent extends ComponentEvent<UserRankingForm> {
    private UserRanking userRanking;

    protected UserFormEvent(UserRankingForm source, UserRanking userRanking) {
      super(source, false);
      this.userRanking = userRanking;
    }

    public UserRanking getUserRanking() {
      return userRanking;
    }
  }

  public static class SaveEvent extends UserFormEvent {
    SaveEvent(UserRankingForm source, UserRanking userRanking) {
      super(source, userRanking);
    }
  }

  public static class DeleteEvent extends UserFormEvent {
    DeleteEvent(UserRankingForm source, UserRanking userRanking) {
      super(source, userRanking);
    }

  }
  public static class CloseEvent extends UserFormEvent {
    CloseEvent(UserRankingForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}