package util;

import slothengine.Transform;

public class Vector2DUtil {
    /**
     * Determines if a point x, y is within a collision box
     *
     * @param transform
     * @param orthoX
     * @param orthoY
     * @return if point (x, y) is within the collision box
     */
    public static boolean isBound(Transform transform, float orthoX, float orthoY) {
        return orthoX >= transform.position.x &&
                orthoX <= transform.position.x + transform.scale.x &&
                orthoY >= transform.position.y &&
                orthoY <= transform.position.y + transform.scale.y;
    }
}
