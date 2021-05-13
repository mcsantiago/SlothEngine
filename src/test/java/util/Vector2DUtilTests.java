package util;

import org.joml.Vector2f;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import slothengine.Transform;

public class Vector2DUtilTests {
    @Test
    public void whenIsBound_returnTrue() {
        Transform transform = new Transform(
                new Vector2f(10, 10),
                new Vector2f(10, 10)
        );
        float orthoX = 15;
        float orthoY = 15;
        Assert.assertTrue(Vector2DUtil.isBound(transform, orthoX, orthoY));
    }

    @Test
    public void whenPointIsOutOfBounds_returnFalse() {
        Transform transform = new Transform(
                new Vector2f(10, 10),
                new Vector2f(10, 10)
        );
        float orthoX = 35;
        float orthoY = 15;
        Assert.assertFalse(Vector2DUtil.isBound(transform, orthoX, orthoY));
    }

    @Test
    public void whenPointIsOnLine_returnTrue() {
        Transform transform = new Transform(
                new Vector2f(10, 10),
                new Vector2f(10, 10)
        );
        float orthoX = 10;
        float orthoY = 15;
        Assert.assertTrue(Vector2DUtil.isBound(transform, orthoX, orthoY));
    }

    @Test
    public void whenPointIsOnPoint_returnTrue() {
        Transform transform = new Transform(
                new Vector2f(10, 10)
        );
        float orthoX = 10;
        float orthoY = 10;
        Assert.assertTrue(Vector2DUtil.isBound(transform, orthoX, orthoY));
    }
}
