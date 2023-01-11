package com.example.application.views.list;

import com.example.application.data.entity.ATP.ATP;
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
public class ATPViewTest {

    @Autowired
    private ATPView atpView;

    @Test
    public void formShownWhenATPSelected() {
        Grid<ATP> grid = atpView.grid;
        ATP firstATP = getFirstItem(grid);

        ATPForm form = atpView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstATP);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstATP.getNickname(), form.nickname.getValue());
    }
    private ATP getFirstItem(Grid<ATP> grid) {
        return( (ListDataProvider<ATP>) grid.getDataProvider()).getItems().iterator().next();
    }
}