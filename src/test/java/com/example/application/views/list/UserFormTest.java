package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.entity.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserFormTest {
    private List<Company> companies;
    private List<Status> statuses;
    private User marcUsher;
    private Company company1;
    private Company company2;
    private Status status1;
    private Status status2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        company1 = new Company();
        company1.setName("Vaadin Ltd");
        company2 = new Company();
        company2.setName("IT Mill");
        companies.add(company1);
        companies.add(company2);

        statuses = new ArrayList<>();
        status1 = new Status();
        status1.setName("Status 1");
        status2 = new Status();
        status2.setName("Status 2");
        statuses.add(status1);
        statuses.add(status2);

        marcUsher = new User();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setStatus(status1);
        marcUsher.setCompany(company2);
    }

@Test
public void formFieldsPopulated() {
    UserForm form = new UserForm(companies, statuses);
    form.setContact(marcUsher);
    Assert.assertEquals("Marc", form.firstName.getValue());
    Assert.assertEquals("Usher", form.lastName.getValue());
    Assert.assertEquals("marc@usher.com", form.email.getValue());
    Assert.assertEquals(company2, form.company.getValue());
    Assert.assertEquals(status1, form.status.getValue());
}

@Test
public void saveEventHasCorrectValues() {
    UserForm form = new UserForm(companies, statuses);
    User user = new User();
    form.setContact(user);
    form.firstName.setValue("John");
    form.lastName.setValue("Doe");
    form.company.setValue(company1);
    form.email.setValue("john@doe.com");
    form.status.setValue(status2);

    AtomicReference<User> savedContactRef = new AtomicReference<>(null);
    form.addListener(UserForm.SaveEvent.class, e -> {
        savedContactRef.set(e.getContact());
    });
    form.save.click();
    User savedUser = savedContactRef.get();

    Assert.assertEquals("John", savedUser.getFirstName());
    Assert.assertEquals("Doe", savedUser.getLastName());
    Assert.assertEquals("john@doe.com", savedUser.getEmail());
    Assert.assertEquals(company1, savedUser.getCompany());
    Assert.assertEquals(status2, savedUser.getStatus());
}
}