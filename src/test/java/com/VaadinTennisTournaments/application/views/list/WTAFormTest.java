package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class WTAFormTest {
    private List<Stage> stage;
    private WTA wta;
    private Stage stage1;
    private Stage stage2;

    @Before
    public void setupData() {
        stage = new ArrayList<>();
        user = new ArrayList<>();

        stage1 = new Stage();
        stage1.setName("QuaterFinal");

        stage2 = new Stage();
        stage2.setName("SemiFinal");

        stage.add(stage1);
        stage.add(stage2);

        user1 = new User();
        user1.setNickname("BW");

        user2 = new User();
        user2.setNickName("BMW");

        stage.add(stage1);
        stage.add(stage2);

        user.add(user1);
        user.add(user2);

        wta = new WTA();
        wta.setWtaTournament("Wimbledon");
        wta.setPlayer("Iga Świątek");
        wta.setStage(stage1);
    }

@Test
public void formFieldsPopulated() {
    WTAForm form = new WTAForm(stage, user);
    form.setWTA(wta);

    Assert.assertEquals("BW", form.user.getValue());
    Assert.assertEquals("Wimbledon", form.wtaTournament.getValue());
    Assert.assertEquals("Iga Świątek", form.player.getValue());
    Assert.assertEquals(stage1, form.stage.getValue());
}

@Test
public void saveEventHasCorrectValues() {
    WTAForm form = new WTAForm(stage, user);
    WTA wta = new WTA();
    form.setWTA(wta);
    form.user.setValue("BW");

    form.stage.setValue(stage1);
    form.wtaTournament.setValue("Australian Open");
    form.player.setValue("Naomi Osaka");

    AtomicReference<WTA> savedWTARef = new AtomicReference<>(null);
    form.addListener(WTAForm.SaveEvent.class, e -> {
        savedWTARef.set(e.getWTA());
    });
    form.save.click();
    WTA savedWTA = savedWTARef.get();

    Assert.assertEquals("BW", savedWTA.getUser,getNickname());
    Assert.assertEquals("Australian Open", savedWTA.getWtaTournament());
    Assert.assertEquals("Naomi Osaka", savedWTA.getPlayer());
    Assert.assertEquals(stage1, savedWTA.getStage());
}
}