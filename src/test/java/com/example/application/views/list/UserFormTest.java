package com.example.application.views.list;

import com.example.application.data.entity.Interests;

import com.example.application.data.entity.User.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserFormTest {
    private List<Interests> interests;
    private User user;
    private Interests interests1;
    private Interests interests2;

    @Before
    public void setupData() {
        interests = new ArrayList<>();

        interests1 = new Interests();
        interests1.setName("ATP Test");
        interests2 = new Interests();
        interests2.setName("WTA Test");

        interests.add(interests1);
        interests.add(interests2);

        user = new User();
        user.setNickname("Marc");
        user.setEmail("marc@user.com");
        user.setInterest(interests2);
    }

@Test
public void formFieldsPopulated() {
    UserForm form = new UserForm(interests);
    form.setUser(user);

    Assert.assertEquals("Marc", form.nickname.getValue());
    Assert.assertEquals("marc@user.com", form.email.getValue());
    Assert.assertEquals(interests2, form.interest.getValue());

}

@Test
public void saveEventHasCorrectValues() {
    UserForm form = new UserForm(interests);
    User user = new User();
    form.setUser(user);
    form.nickname.setValue("John");

    form.interest.setValue(interests1);
    form.email.setValue("john@doe.com");

    AtomicReference<User> savedUserRef = new AtomicReference<>(null);
    form.addListener(UserForm.SaveEvent.class, e -> {
        savedUserRef.set(e.getUser());
    });
    form.save.click();
    User savedUser = savedUserRef.get();

    Assert.assertEquals("John", savedUser.getNickname());
    Assert.assertEquals("john@doe.com", savedUser.getEmail());
    Assert.assertEquals(interests1, savedUser.getInterest());

}
}