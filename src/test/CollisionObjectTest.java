package test;

import code.model.CollisionObject;
import code.model.Direction;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class CollisionObjectTest {

    //mock-class
    class SomeObj extends CollisionObject {
        public SomeObj(int x, int y) {
            super(x, y);
        }

        @Override
        public void draw(Graphics graphics) {

        }
    }

    @Test
    public void isCollision() {
        SomeObj someObjOrig = new SomeObj(100, 100);

        //с этими объектами должно быть пересечение isCollision() == true
        SomeObj someObjUp = new SomeObj(100, 80);
        SomeObj someObjRight = new SomeObj(120, 100);
        SomeObj someObjDown = new SomeObj(100, 120);
        SomeObj someObjLeft = new SomeObj(80, 100);

        //до этих объектов не хватает одной точки до полного пересечения isCollision() == false
        SomeObj someObjUpWrong = new SomeObj(100, 79);
        SomeObj someObjRightWrong = new SomeObj(121, 100);
        SomeObj someObjDownWrong = new SomeObj(100, 121);
        SomeObj someObjLeftWrong = new SomeObj(79, 100);

        Assert.assertTrue("up", someObjOrig.isCollision(someObjUp, Direction.UP));
        Assert.assertTrue("right", someObjOrig.isCollision(someObjRight, Direction.RIGHT));
        Assert.assertTrue("down", someObjOrig.isCollision(someObjDown, Direction.DOWN));
        Assert.assertTrue("left", someObjOrig.isCollision(someObjLeft, Direction.LEFT));

        Assert.assertFalse("up-wrong", someObjOrig.isCollision(someObjUpWrong, Direction.UP));
        Assert.assertFalse("right-wrong", someObjOrig.isCollision(someObjRightWrong, Direction.RIGHT));
        Assert.assertFalse("down-wrong", someObjOrig.isCollision(someObjDownWrong, Direction.DOWN));
        Assert.assertFalse("left-wrong", someObjOrig.isCollision(someObjLeftWrong, Direction.LEFT));

    }

}