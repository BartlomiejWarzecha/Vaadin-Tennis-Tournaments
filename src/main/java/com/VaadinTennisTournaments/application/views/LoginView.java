package com.VaadinTennisTournaments.application.views;

import com.VaadinTennisTournaments.application.data.entity.Register.RegisterUser;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.list.RegistrationForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("login")
@PageTitle("Login | Vaadin Tennis Tournaments")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	Grid<RegisterUser> grid = new Grid<>(RegisterUser.class);
	private  LoginForm login = new LoginForm();
	RegistrationForm form;
	MainService mainService;
	Notification notification = Notification
			.show("Welcome, Have Fun!");
	public LoginView(MainService mainService) {

		addClassName("login-view");
		setSizeFull();
		configureGrid();
		configureForm();

		Icon outlineIcon = new Icon(VaadinIcon.MOON);
		outlineIcon.setColor("#ffff00");
		outlineIcon.setSize("75px");

		login.setForgotPasswordButtonVisible(false);

		add(outlineIcon, new H2("Vaadin Tennis Tournaments!" ) , login,
				getToolbar(), getContent() );

		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setHorizontalComponentAlignment(Alignment.CENTER);

		login.setAction("login");


		setHeight("1200px");
		setWidth("450px");
		setMargin(true);
		closeEditor();
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if (beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters()
				.containsKey("error")) {
			login.setError(true);
		}


	}

	private HorizontalLayout getContent() {
		HorizontalLayout content = new HorizontalLayout(form);
		content.addClassNames("content");
		content.setSizeFull();
		content.setAlignItems(Alignment.CENTER);
		setHorizontalComponentAlignment(Alignment.CENTER);
		return content;
	}

	private void configureForm() {
		form = new RegistrationForm();
		form.setWidth("25em");
		form.setHeight("40em");

		form.addListener(RegistrationForm.SaveEvent.class, this::saveRegisterUser);
	}
	private void configureGrid() {
		grid.addClassNames("atp-grid");
		grid.setSizeFull();
		grid.setColumns("username", "email", "password");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
		grid.asSingleSelect().addValueChangeListener(event ->
				editRegisterUser(event.getValue()));
	}

	private HorizontalLayout getToolbar() {

		Button registerButton = new Button("Register");
		registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
		registerButton.addClickListener(click -> addRegisterUser());

		HorizontalLayout toolbar = new HorizontalLayout(registerButton);
		toolbar.addClassName("toolbar-Register");
		return toolbar;
	}

	private void saveRegisterUser(RegistrationForm.SaveEvent event) {
		mainService.saveRegisterUser(event.getRegisterUser());
	}
	private void deleteRegisterUser(RegistrationForm.DeleteEvent event) {
		mainService.deleteRegisterUser(event.getRegisterUser());

		closeEditor();
	}
	public void editRegisterUser(RegisterUser registerUser) {
		if (registerUser == null) {
			closeEditor();
		} else {

			form.setRegisterUser(registerUser);
			form.setVisible(true);
			addClassName("editing");
		}
	}

	private void addRegisterUser() {
		grid.asSingleSelect().clear();
		editRegisterUser(new RegisterUser());
	}
	private void closeEditor() {
		form.setRegisterUser(null);
		form.setVisible(false);
		removeClassName("editing");
	}


}