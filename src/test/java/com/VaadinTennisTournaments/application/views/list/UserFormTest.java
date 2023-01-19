package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Interests;
import com.VaadinTennisTournaments.application.data.entity.User.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserFormTest {
    private List<Interests> interests;
    private User user;
    private Interests interest1;
    private Interests interest2;

    @Before
    public void setupData() {
        interests = new ArrayList<>();

        interest1 = new Interests();
        interest1.setName("ATP Test");
        interest2 = new Interests();
        interest2.setName("WTA Test");

        interests.add(interest1);
        interests.add(interest2);

        user = new User();
        user.setNickname("Marc");
        user.setEmail("marc@user.com");
        user.setInterest(interest2);
    }

@Test
public void formFieldsPopulated() {
    UserForm form = new UserForm(interests);
    form.setUser(user);

    Assert.assertEquals("Marc", form.nickname.getValue());
    Assert.assertEquals("marc@user.com", form.email.getValue());
    Assert.assertEquals(interest2, form.interest.getValue());

}

@Test
public void saveEventHasCorrectValues() {
    UserForm form = new UserForm(interests);
    User user = new User();
    form.setUser(user);
    form.nickname.setValue("John");

    form.interest.setValue(interest1);
    form.email.setValue("john@doe.com");

    AtomicReference<User> savedUserRef = new AtomicReference<>(null);
    form.addListener(UserForm.SaveEvent.class, e -> {
        savedUserRef.set(e.getUser());
    });
    form.save.click();
    User savedUser = savedUserRef.get();

    Assert.assertEquals("John", savedUser.getNickname());
    Assert.assertEquals("john@doe.com", savedUser.getEmail());
    Assert.assertEquals(interest1, savedUser.getInterest());

}
}