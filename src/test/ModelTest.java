package test;

import code.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {
    Model model = new Model();

    @Before
    public void setUp() throws Exception {
        model.restartLevel(1);
    }

    @Test
    public void checkBoxCollisionAndMoveIfAvailable() {
        Assert.assertTrue("box + box", model.checkBoxCollisionAndMoveIfAvailable(Direction.UP));
        Assert.assertFalse("box + home", model.checkBoxCollisionAndMoveIfAvailable(Direction.RIGHT));
        Assert.assertTrue("box + wall", model.checkBoxCollisionAndMoveIfAvailable(Direction.DOWN));
        Assert.assertFalse("box + none", model.checkBoxCollisionAndMoveIfAvailable(Direction.LEFT));
    }
}