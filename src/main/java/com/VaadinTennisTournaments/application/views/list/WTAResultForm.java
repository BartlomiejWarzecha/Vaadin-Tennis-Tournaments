package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.wta.WTAPlayer;
import com.VaadinTennisTournaments.application.data.entity.wta.WTAResult;
import com.VaadinTennisTournaments.application.data.entity.wta.WTATournament;
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

public class WTAResultForm extends FormLayout {
  private WTAResult WTAResult;
  ComboBox<WTAPlayer> winner = new ComboBox<WTAPlayer>("Winner");
  ComboBox<WTATournament> tournament = new ComboBox<WTATournament>("Tournament");

  Binder<WTAResult> binder = new BeanValidationBinder<>(WTAResult.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

    public WTAResultForm(List<WTAPlayer> wtaPlayers, List<WTATournament> wtaTournaments) {
    addClassName("WTAResult-form");
    binder.bindInstanceFields(this);

      winner.setItems(wtaPlayers);
      winner.setItemLabelGenerator(WTAPlayer::getFullname);
      tournament.setItems(wtaTournaments);
      tournament.setItemLabelGenerator(WTATournament::getTournament);

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
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, WTAResult)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setWTAResult(WTAResult WTAResult) {
    this.WTAResult = WTAResult;
    binder.readBean(WTAResult);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(WTAResult);
      fireEvent(new SaveEvent(this, WTAResult));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class WTAResultFormEvent extends ComponentEvent<WTAResultForm> {
    private WTAResult WTAResult;

    protected WTAResultFormEvent(WTAResultForm source, WTAResult WTAResult) {
      super(source, false);
      this.WTAResult = WTAResult;
    }

    public WTAResult getWTAResult() {
      return WTAResult;
    }
  }

  public static class SaveEvent extends WTAResultFormEvent {
    SaveEvent(WTAResultForm source, WTAResult WTAResult) {
      super(source, WTAResult);
    }
  }

  public static class DeleteEvent extends WTAResultFormEvent {
    DeleteEvent(WTAResultForm source, WTAResult WTAResult) {
      super(source, WTAResult);
    }

  }

  public static class CloseEvent extends WTAResultFormEvent {
    CloseEvent(WTAResultForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}