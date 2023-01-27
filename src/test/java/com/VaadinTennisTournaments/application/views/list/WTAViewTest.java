package com.VaadinTennisTournaments.application.views.list;

import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
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
public class WTAViewTest {

    @Autowired
    private WTAView wtaView;

    @Test
    public void formShownWhenUserSelected() {
        Grid<WTA> grid = wtaView.grid;
        WTA firstWTA = getFirstItem(grid);

        WTAForm form = wtaView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstWTA);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstWTA.getNickname(), form.user.getValue());
    }
    private WTA getFirstItem(Grid<WTA> grid) {
        return( (ListDataProvider<WTA>) grid.getDataProvider()).getItems().iterator().next();
    }
}