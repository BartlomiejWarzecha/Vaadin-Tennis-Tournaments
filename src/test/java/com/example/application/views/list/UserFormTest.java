package com.example.application.views.list;

import Interests;
import com.example.application.data.entity.User.User;
import Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserFormTest {
    private List<Interests> companies;
    private List<Status> statuses;
    private User marcUsher;
    private Interests interests1;
    private Interests interests2;
    private Status status1;
    private Status status2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        interests1 = new Interests();
        interests1.setName("Vaadin Ltd");
        interests2 = new Interests();
        interests2.setName("IT Mill");
        companies.add(interests1);
        companies.add(interests2);

        statuses = new ArrayList<>();
        status1 = new Status();
        status1.setName("Status 1");
        status2 = new Status();
        status2.setName("Status 2");
        statuses.add(status1);
        statuses.add(status2);

        marcUsher = new User();
        marcUsher.setNickname("Marc");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setInterest(interests2);
    }

@Test
public void formFieldsPopulated() {
    UserForm form = new UserForm(companies);
    form.setContact(marcUsher);
    Assert.assertEquals("Marc", form.nickname.getValue());

    Assert.assertEquals("marc@usher.com", form.email.getValue());
    Assert.assertEquals(interests2, form.interest.getValue());

}

@Test
public void saveEventHasCorrectValues() {
    UserForm form = new UserForm(companies);
    User user = new User();
    form.setContact(user);
    form.nickname.setValue("John");

    form.interest.setValue(interests1);
    form.email.setValue("john@doe.com");

    AtomicReference<User> savedContactRef = new AtomicReference<>(null);
    form.addListener(UserForm.SaveEvent.class, e -> {
        savedContactRef.set(e.getContact());
    });
    form.save.click();
    User savedUser = savedContactRef.get();

    Assert.assertEquals("John", savedUser.getNickname());
    Assert.assertEquals("john@doe.com", savedUser.getEmail());
    Assert.assertEquals(interests1, savedUser.getInterest());

}
}