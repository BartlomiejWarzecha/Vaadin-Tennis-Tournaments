package com.VaadinTennisTournaments.application.views.list;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAPunctation;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDateTime;
import java.util.List;

public class WTAPunctationForm extends FormLayout {
  private WTAPunctation WTAPunctation;
  TextField points = new TextField("Points");

  ComboBox<WTA>  wtaTournament = new ComboBox<>("Tournament");
  ComboBox<Rank> rank = new ComboBox<>("Rank");
  ComboBox<Stage> stage = new ComboBox<>("Stage");
  ComboBox<User> user = new ComboBox<>("User");


  Binder<WTAPunctation> binder = new BeanValidationBinder<>(WTAPunctation.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public WTAPunctationForm(List<Rank> ranks,
                           List<Stage> stages, List<User> users, List<WTA> wtaTournaments) {
    addClassName("WTAPunctation-form");
    binder.bindInstanceFields(this);

    rank.setItems(ranks);
    rank.setItemLabelGenerator(Rank::getName);
    stage.setItems(stages);
    stage.setItemLabelGenerator(Stage::getName);
    user.setItems(users);
    user.setItemLabelGenerator(User::getNickname);

    wtaTournament.setItems(wtaTournaments);
    wtaTournament.setItemLabelGenerator(WTA::getWtaTournament);

    add(    user,
            wtaTournament,
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
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, WTAPunctation)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setWTAPunctation(WTAPunctation WTAPunctation) {
    this.WTAPunctation = WTAPunctation;
    binder.readBean(WTAPunctation);
  }
  private void validateAndSave() {
    try {
      binder.writeBean(WTAPunctation);
      fireEvent(new SaveEvent(this, WTAPunctation));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class PunctationFormEvent extends ComponentEvent<WTAPunctationForm> {
    private WTAPunctation WTAPunctation;

    protected PunctationFormEvent(WTAPunctationForm source, WTAPunctation WTAPunctation) {
      super(source, false);
      this.WTAPunctation = WTAPunctation;
    }

    public WTAPunctation getPunctation() {
      return WTAPunctation;
    }
  }

  public static class SaveEvent extends PunctationFormEvent {
    SaveEvent(WTAPunctationForm source, WTAPunctation WTAPunctation) {
      super(source, WTAPunctation);
    }
  }

  public static class DeleteEvent extends PunctationFormEvent {
    DeleteEvent(WTAPunctationForm source, WTAPunctation WTAPunctation) {
      super(source, WTAPunctation);
    }

  }

  public static class CloseEvent extends PunctationFormEvent {
    CloseEvent(WTAPunctationForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}