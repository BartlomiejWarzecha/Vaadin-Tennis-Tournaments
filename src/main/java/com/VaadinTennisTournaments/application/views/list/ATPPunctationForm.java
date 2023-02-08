package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATP;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPunctation;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
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

public class ATPPunctationForm extends FormLayout {
  private ATPPunctation atpPunctation;
  TextField points = new TextField("Points");
  ComboBox<ATP> atpTournament = new ComboBox<>("ATP Tournament");
  ComboBox<Interests> interest = new ComboBox<>("Type");
  ComboBox<Rank> rank = new ComboBox<>("Rank");
  ComboBox<Stage> stage = new ComboBox<>("Stage");
  ComboBox<User> user = new ComboBox<>("User");
  Binder<ATPPunctation> binder = new BeanValidationBinder<>(ATPPunctation.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public ATPPunctationForm(
                           List<Rank> ranks, List<Stage> stages,
                           List<User> users, List<ATP> atpTournaments) {
    addClassName("atpPunctation-form");
    binder.bindInstanceFields(this);

    rank.setItems(ranks);
    rank.setItemLabelGenerator(Rank::getName);
    stage.setItems(stages);
    stage.setItemLabelGenerator(Stage::getName);
    user.setItems(users);
    user.setItemLabelGenerator(User::getNickname);

    atpTournament.setItems(atpTournaments);
    atpTournament.setItemLabelGenerator(ATP::getAtpTournament);

    add(
            points,
            user,
            stage,
            atpTournament,
          rank,
          createButtonsLayout());
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, atpPunctation)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setATPPunctation(ATPPunctation atpPunctation) {
    this.atpPunctation = atpPunctation;
    binder.readBean(atpPunctation);
  }
  private void validateAndSave() {
    try {
      binder.writeBean(atpPunctation);
      fireEvent(new SaveEvent(this, atpPunctation));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class PunctationFormEvent extends ComponentEvent<ATPPunctationForm> {
    private ATPPunctation atpPunctation;

    protected PunctationFormEvent(ATPPunctationForm source, ATPPunctation atpPunctation) {
      super(source, false);
      this.atpPunctation = atpPunctation;
    }

    public ATPPunctation getATPPunctation() {
      return atpPunctation;
    }
  }

  public static class SaveEvent extends PunctationFormEvent {
    SaveEvent(ATPPunctationForm source, ATPPunctation atpPunctation) {
      super(source, atpPunctation);
    }
  }

  public static class DeleteEvent extends PunctationFormEvent {
    DeleteEvent(ATPPunctationForm source, ATPPunctation atpPunctation) {
      super(source, atpPunctation);
    }

  }

  public static class CloseEvent extends PunctationFormEvent {
    CloseEvent(ATPPunctationForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}