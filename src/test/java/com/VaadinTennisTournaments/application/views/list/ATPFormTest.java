package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Stage;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ATPFormTest {
    private List<Stage> stage;
    private ATP atp;
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

        atp = new ATP();
        atp.setNickname("BW");
        atp.setAtpTournament("Wimbledon");
        atp.setPlayer("Hubert Hurkacz");
        atp.setStage(stage1);
    }

@Test
public void formFieldsPopulated() {
    ATPForm form = new ATPForm(stage);
    form.setATP(atp);

    Assert.assertEquals("BW", form.nickname.getValue());
    Assert.assertEquals("Wimbledon", form.atpTournament.getValue());
    Assert.assertEquals("Hubert Hurkacz", form.player.getValue());
    Assert.assertEquals(stage1, form.stage.getValue());
}

@Test
public void saveEventHasCorrectValues() {
    ATPForm form = new ATPForm(stage);
    ATP atp = new ATP();
    form.setATP(atp);
    form.nickname.setValue("BW");
    form.stage.setValue(stage1);
    form.atpTournament.setValue("Australian Open");
    form.player.setValue("Rafael Nadal");

    AtomicReference<ATP> savedATPRef = new AtomicReference<>(null);
    form.addListener(ATPForm.SaveEvent.class, e -> {
        savedATPRef.set(e.getATP());
    });
    form.save.click();
    ATP savedATP = savedATPRef.get();

    Assert.assertEquals("BW", savedATP.getNickname());
    Assert.assertEquals("Australian Open", savedATP.getAtpTournament());
    Assert.assertEquals("Rafael Nadal", savedATP.getPlayer());
    Assert.assertEquals(stage1, savedATP.getStage());

}
}