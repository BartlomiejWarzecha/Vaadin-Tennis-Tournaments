package com.example.application.views.list;

import com.example.application.data.entity.User;
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
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<User> grid = listView.grid;
        User firstUser = getFirstItem(grid);

        UserForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstUser);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstUser.getNickname(), form.nickname.getValue());
    }
    private User getFirstItem(Grid<User> grid) {
        return( (ListDataProvider<User>) grid.getDataProvider()).getItems().iterator().next();
    }
}