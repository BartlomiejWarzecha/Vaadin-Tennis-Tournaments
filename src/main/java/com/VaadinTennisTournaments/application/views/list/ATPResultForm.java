package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPResult;
import com.VaadinTennisTournaments.application.data.entity.tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
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
import com.VaadinTennisTournaments.application.data.entity.atp.ATPPlayer;
import com.VaadinTennisTournaments.application.data.entity.atp.ATPTournament;
import java.util.List;

public class ATPResultForm extends FormLayout {
  private ATPResult ATPResult;
  ComboBox<ATPPlayer> winner = new ComboBox<ATPPlayer>("Winner");
  ComboBox<ATPTournament> tournament = new ComboBox<ATPTournament>("Tournament");

  Binder<ATPResult> binder = new BeanValidationBinder<>(ATPResult.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public ATPResultForm(
                       List<ATPPlayer> winners, List<ATPTournament> atpTournaments) {

    addClassName("ATPResult-form");
    binder.bindInstanceFields(this);
    winner.setItems(winners);
    winner.setItemLabelGenerator(ATPPlayer::getFullname);
    tournament.setItems(atpTournaments);
    tournament.setItemLabelGenerator(ATPTournament::getTournament);


    add(tournament,
          winner,
          createButtonsLayout());
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, ATPResult)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setATPResult(ATPResult atpResult) {
    this.ATPResult = atpResult;
    binder.readBean(atpResult);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(ATPResult);
      fireEvent(new SaveEvent(this, ATPResult));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class ATPResultFormEvent extends ComponentEvent<ATPResultForm> {
    private ATPResult ATPResult;

    protected ATPResultFormEvent(ATPResultForm source, ATPResult ATPResult) {
      super(source, false);
      this.ATPResult = ATPResult;
    }

    public ATPResult getATPResult() {
      return ATPResult;
    }
  }

  public static class SaveEvent extends ATPResultFormEvent {
    SaveEvent(ATPResultForm source, ATPResult atpResult) {
      super(source, atpResult);
    }
  }

  public static class DeleteEvent extends ATPResultFormEvent {
    DeleteEvent(ATPResultForm source, ATPResult ATPResult) {
      super(source, ATPResult);
    }

  }

  public static class CloseEvent extends ATPResultFormEvent {
    CloseEvent(ATPResultForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}