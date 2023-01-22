package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Stage;
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

        stage1 = new Stage();
        stage1.setName("QuaterFinal");

        stage2 = new Stage();
        stage2.setName("SemiFinal");

        stage.add(stage1);
        stage.add(stage2);

        wta = new WTA();
        wta.setNickname("BW");
        wta.setWtaTournament("Wimbledon");
        wta.setPlayer("Iga Świątek");
        wta.setStage(stage1);
    }

@Test
public void formFieldsPopulated() {
    WTAForm form = new WTAForm(stage);
    form.setWTA(wta);

    Assert.assertEquals("BW", form.nickname.getValue());
    Assert.assertEquals("Wimbledon", form.wtaTournament.getValue());
    Assert.assertEquals("Iga Świątek", form.player.getValue());
    Assert.assertEquals(stage1, form.stage.getValue());
}

@Test
public void saveEventHasCorrectValues() {
    WTAForm form = new WTAForm(stage);
    WTA wta = new WTA();
    form.setWTA(wta);
    form.nickname.setValue("BW");

    form.stage.setValue(stage1);
    form.wtaTournament.setValue("Australian Open");
    form.player.setValue("Naomi Osaka");

    AtomicReference<WTA> savedWTARef = new AtomicReference<>(null);
    form.addListener(WTAForm.SaveEvent.class, e -> {
        savedWTARef.set(e.getWTA());
    });
    form.save.click();
    WTA savedWTA = savedWTARef.get();

    Assert.assertEquals("BW", savedWTA.getNickname());
    Assert.assertEquals("Australian Open", savedWTA.getWtaTournament());
    Assert.assertEquals("Naomi Osaka", savedWTA.getPlayer());
    Assert.assertEquals(stage1, savedWTA.getStage());
}
}