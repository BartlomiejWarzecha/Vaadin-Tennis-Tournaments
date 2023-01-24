package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.Interests;
import com.VaadinTennisTournaments.application.data.entity.Punctation.Punctation;
import com.VaadinTennisTournaments.application.data.entity.Rank;
import com.VaadinTennisTournaments.application.data.entity.Result.Result;
import com.VaadinTennisTournaments.application.data.entity.Stage;
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

public class PunctationForm extends FormLayout {
  private Punctation punctation;
  TextField tournament = new TextField("Tournament");
  TextField nickname = new TextField("Nickname");
  ComboBox<Interests> interest = new ComboBox<>("Type");
  ComboBox<Rank> rank = new ComboBox<>("Rank");
  ComboBox<Stage> stage = new ComboBox<>("Stage");
  TextField points = new TextField("Points");
  Binder<Punctation> binder = new BeanValidationBinder<>(Punctation.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public PunctationForm(List<Interests> interests, List<Rank> ranks, List<Stage> stages) {
    addClassName("punctation-form");
    binder.bindInstanceFields(this);

    interest.setItems(interests);
    interest.setItemLabelGenerator(Interests::getName);
    rank.setItems(ranks);
    rank.setItemLabelGenerator(Rank::getName);
    stage.setItems(stages);
    stage.setItemLabelGenerator(Stage::getName);

    add(tournament,
            nickname,
          interest,
          rank,
          stage,
          points,
          createButtonsLayout());
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, punctation)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setPunctation(Punctation punctation) {
    this.punctation = punctation;
    binder.readBean(punctation);
  }
  private void validateAndSave() {
    try {
      binder.writeBean(punctation);
      fireEvent(new SaveEvent(this, punctation));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class PunctationFormEvent extends ComponentEvent<PunctationForm> {
    private Punctation punctation;

    protected PunctationFormEvent(PunctationForm source, Punctation punctation) {
      super(source, false);
      this.punctation = punctation;
    }

    public Punctation getPunctation() {
      return punctation;
    }
  }

  public static class SaveEvent extends PunctationFormEvent {
    SaveEvent(PunctationForm source, Punctation punctation) {
      super(source, punctation);
    }
  }

  public static class DeleteEvent extends PunctationFormEvent {
    DeleteEvent(PunctationForm source, Punctation punctation) {
      super(source, punctation);
    }

  }

  public static class CloseEvent extends PunctationFormEvent {
    CloseEvent(PunctationForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}