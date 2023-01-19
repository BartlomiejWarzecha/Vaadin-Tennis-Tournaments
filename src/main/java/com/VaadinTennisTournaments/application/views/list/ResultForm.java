package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.Interests;
import com.VaadinTennisTournaments.application.data.entity.Rank;
import com.VaadinTennisTournaments.application.data.entity.Result.Result;
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

public class ResultForm extends FormLayout {
  private Result result;
  TextField tournament = new TextField("Tournament");
  TextField winner = new TextField("Winner");
  ComboBox<Interests> interest = new ComboBox<>("Type");
  ComboBox<Rank> rank = new ComboBox<>("Rank");
  Binder<Result> binder = new BeanValidationBinder<>(Result.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public ResultForm(List<Interests> interests, List<Rank> ranks) {
    addClassName("result-form");
    binder.bindInstanceFields(this);

    interest.setItems(interests);
    interest.setItemLabelGenerator(Interests::getName);
    rank.setItems(ranks);
    rank.setItemLabelGenerator(Rank::getName);

    add(tournament,
          winner,
          interest,
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
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, result)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setResult(Result result) {
    this.result = result;
    binder.readBean(result);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(result);
      fireEvent(new SaveEvent(this, result));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class ResultFormEvent extends ComponentEvent<ResultForm> {
    private Result result;

    protected ResultFormEvent(ResultForm source, Result result) {
      super(source, false);
      this.result = result;
    }

    public Result getResult() {
      return result;
    }
  }

  public static class SaveEvent extends ResultFormEvent {
    SaveEvent(ResultForm source, Result result) {
      super(source, result);
    }
  }

  public static class DeleteEvent extends ResultFormEvent {
    DeleteEvent(ResultForm source, Result result) {
      super(source, result);
    }

  }

  public static class CloseEvent extends ResultFormEvent {
    CloseEvent(ResultForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}