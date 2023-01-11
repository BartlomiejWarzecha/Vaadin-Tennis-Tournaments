package com.example.application.views.list;

import com.example.application.data.entity.Stage;
import com.example.application.data.entity.WTA.WTA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ATPFormTest {
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
    WTA savedUser = savedWTARef.get();

    Assert.assertEquals("BW", savedUser.getNickname());
    Assert.assertEquals("Australian Open", form.wtaTournament.getValue());
    Assert.assertEquals("Naomi Osaka", form.player.getValue());
    Assert.assertEquals(stage1, form.stage.getValue());

}
}