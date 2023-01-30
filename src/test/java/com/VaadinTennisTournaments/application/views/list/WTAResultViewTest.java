package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.WTA.WTAResult;
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
public class WTAResultViewTest {

    @Autowired
    private WTAResultView WTAResultView;

    @Test
    public void formShownWhenResultSelected() {
        Grid<WTAResult> grid = WTAResultView.grid;
        WTAResult firstWTAResult = getFirstItem(grid);

        WTAResultForm form = WTAResultView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstWTAResult);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstWTAResult.getTournament(), form.tournament.getValue());
    }
    private WTAResult getFirstItem(Grid<WTAResult> grid) {
        return( (ListDataProvider<WTAResult>) grid.getDataProvider()).getItems().iterator().next();
    }
}