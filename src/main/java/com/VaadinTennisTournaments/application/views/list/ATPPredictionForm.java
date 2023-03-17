package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.atp.ATP;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPPlayer;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPTournament;
import com.VaadinTennisTournaments.application.data.entity.tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.user.User;
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

public class ATPPredictionForm extends FormLayout {
  private ATP atp;
  TextField nickname = new TextField("Nickname");
  ComboBox<ATPTournament> atpTournament = new ComboBox<ATPTournament>("ATP Tournament");
  ComboBox<ATPPlayer> player = new ComboBox<ATPPlayer>("Player");
  ComboBox<Stage> stage = new ComboBox<>("Stage");
  ComboBox<User> user = new ComboBox<>("User");
  Binder<ATP> binder = new BeanValidationBinder<>(ATP.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public ATPPredictionForm(List<Stage> stages, List<User> users,
                            List<ATPTournament> atpTournaments,
                            List<ATPPlayer> atpPlayers) {
    addClassName("atp-form");
    binder.bindInstanceFields(this);
    stage.setItems(stages);
    stage.setItemLabelGenerator(Stage::getName);

    user.setItems(users);
    user.setItemLabelGenerator(User::getNickname);

    atpTournament.setItems(atpTournaments);
    atpTournament.setItemLabelGenerator(ATPTournament::getTournament);

    player.setItems(atpPlayers);
    player.setItemLabelGenerator(ATPPlayer::getFullname);

    add(user,
            atpTournament,
            player,
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
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, atp)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setATP(ATP atp) {
    this.atp = atp;
    binder.readBean(atp);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(atp);
      fireEvent(new SaveEvent(this, atp));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class ATPFormEvent extends ComponentEvent<ATPPredictionForm> {
    private ATP atp;

    protected ATPFormEvent(ATPPredictionForm source, ATP atp) {
      super(source, false);
      this.atp = atp;
    }

    public ATP getATP() {
      return atp;
    }
  }

  public static class SaveEvent extends ATPFormEvent {
    SaveEvent(ATPPredictionForm source, ATP atp) {
      super(source, atp);
    }
  }

  public static class DeleteEvent extends ATPFormEvent {
    DeleteEvent(ATPPredictionForm source, ATP atp) {
      super(source, atp);
    }

  }

  public static class CloseEvent extends ATPFormEvent {
    CloseEvent(ATPPredictionForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}