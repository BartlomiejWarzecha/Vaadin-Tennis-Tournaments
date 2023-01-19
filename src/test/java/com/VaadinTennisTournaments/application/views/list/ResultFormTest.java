package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Interests;
import com.VaadinTennisTournaments.application.data.entity.Rank;
import com.VaadinTennisTournaments.application.data.entity.Result.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ResultFormTest {
    private List<Interests> interests;
    private List<Rank> ranks;
    private Result result;
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

        result = new Result();
        result.setTournament("Test Tournament");
        result.setWinner("Ash Barty");
        result.setInterest(interest2);
        result.setRank(rank2);
    }
@Test
public void formFieldsPopulated() {
    ResultForm form = new ResultForm(interests, ranks);
    form.setResult(result);

    Assert.assertEquals("Test Tournament", form.tournament.getValue());
    Assert.assertEquals("Ash Barty", form.winner.getValue());
    Assert.assertEquals(interest2, form.interest.getValue());
    Assert.assertEquals(rank2, form.rank.getValue());
}

@Test
public void saveEventHasCorrectValues() {
    ResultForm form = new ResultForm(interests,ranks);
    Result result = new Result();
    form.setResult(result);

    form.tournament.setValue("Tournament");
    form.winner.setValue("Best Player");
    form.interest.setValue(interest1);
    form.rank.setValue(rank1);

    AtomicReference<Result> savedResultRef = new AtomicReference<>(null);
    form.addListener(ResultForm.SaveEvent.class, e -> {
        savedResultRef.set(e.getResult());
    });
    form.save.click();
    Result savedResult = savedResultRef.get();

    Assert.assertEquals("Tournament", savedResult.getTournament());
    Assert.assertEquals("Best Player", savedResult.getWinner());
    Assert.assertEquals(interest1, savedResult.getInterest());
    Assert.assertEquals(rank1, savedResult.getRank());

}
}