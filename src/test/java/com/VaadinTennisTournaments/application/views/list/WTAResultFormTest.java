package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class WTAResultFormTest {
    private List<Interests> interests;
    private List<Rank> ranks;
    private WTAResult WTAResult;
    private Interests interest1;
    private Interests interest2;

    private Rank rank1;
    private Rank rank2;
    @Before
    public void setupData() {
        interests = new ArrayList<>();
        ranks = new ArrayList<>();

        interest1 = new Interests();
        interest2 = new Interests();

        rank1 = new Rank();
        rank2 = new Rank();

        interest1.setName("ATP Test");
        interest2.setName("WTA Test");
        interests.add(interest1);
        interests.add(interest2);

        rank1.setName("ATP 500 Test");
        rank2.setName("WTA 1000 test");
        ranks.add(rank1);
        ranks.add(rank2);

        WTAResult = new WTAResult();
        WTAResult.setTournament("Test Tournament");
        WTAResult.setWinner("Ash Barty");
        WTAResult.setInterest(interest2);
        WTAResult.setRank(rank2);
    }
@Test
public void formFieldsPopulated() {
    WTAResultForm form = new WTAResultForm(interests, ranks);
    form.setResult(WTAResult);

    Assert.assertEquals("Test Tournament", form.tournament.getValue());
    Assert.assertEquals("Ash Barty", form.winner.getValue());
    Assert.assertEquals(interest2, form.interest.getValue());
    Assert.assertEquals(rank2, form.rank.getValue());
}

@Test
public void saveEventHasCorrectValues() {
    WTAResultForm form = new WTAResultForm(interests,ranks);
    WTAResult WTAResult = new WTAResult();
    form.setResult(WTAResult);

    form.tournament.setValue("Tournament");
    form.winner.setValue("Best Player");
    form.interest.setValue(interest1);
    form.rank.setValue(rank1);

    AtomicReference<WTAResult> savedResultRef = new AtomicReference<>(null);
    form.addListener(WTAResultForm.SaveEvent.class, e -> {
        savedResultRef.set(e.getWTAResult());
    });
    form.save.click();
    WTAResult savedWTAResult = savedResultRef.get();

    Assert.assertEquals("Tournament", savedWTAResult.getTournament());
    Assert.assertEquals("Best Player", savedWTAResult.getWinner());
    Assert.assertEquals(interest1, savedWTAResult.getInterest());
    Assert.assertEquals(rank1, savedWTAResult.getRank());

}
}