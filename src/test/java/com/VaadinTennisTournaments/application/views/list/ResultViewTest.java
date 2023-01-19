package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.Result.Result;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultViewTest {

    @Autowired
    private ResultView resultView;

    @Test
    public void formShownWhenResultSelected() {
        Grid<Result> grid = resultView.grid;
        Result firstResult = getFirstItem(grid);

        ResultForm form = resultView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstResult);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstResult.getTournament(), form.tournament.getValue());
    }
    private Result getFirstItem(Grid<Result> grid) {
        return( (ListDataProvider<Result>) grid.getDataProvider()).getItems().iterator().next();
    }
}