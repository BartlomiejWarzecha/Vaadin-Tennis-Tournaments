package com.VaadinTennisTournaments.application.views;

import com.VaadinTennisTournaments.application.data.entity.Register.RegisterUser;
import com.VaadinTennisTournaments.application.data.service.MainService;
import com.VaadinTennisTournaments.application.views.list.RegistrationForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("login")
@PageTitle("Login | Vaadin Tennis Tournaments")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	Grid<RegisterUser> grid = new Grid<>(RegisterUser.class);
	private final LoginForm login = new LoginForm();
	RegistrationForm form;
	MainService mainService;

	public LoginView(MainService mainService) {
		this.mainService = mainService;
		addClassName("login-view");
		setSizeFull();
		configureGrid();
		configureForm();


		add(new H2("Vaadin Tennis Tournaments!"), login, getToolbar(), getContent(), getHrefParagraph("WTA Tennis", "ATP Tour"));

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


	private Paragraph getHrefParagraph(String hrefValue , String description){

		String pureHrefValue = hrefValue.replaceAll("\\s", "");// value without spaces

		Anchor href = new Anchor("https://www."+pureHrefValue+".com/", "here");

		Paragraph paragraph = new Paragraph(new Text("Click "), href, new Text(" to see official details about " + description));

		return paragraph;
	}
}