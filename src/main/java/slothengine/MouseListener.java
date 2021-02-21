package slothengine;

import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance = null;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private final boolean[] mouseButtonPressed = new boolean[9];
    private boolean isDragging;

    private MouseListener() {}

    public static MouseListener getInstance() {
        if (instance == null) {
            instance = new MouseListener();
        }
        return instance;
    }

    public static void mousePosCallback(long windowId, double xPos, double yPos) {
        MouseListener instance = getInstance();
        instance.lastX = instance.xPos;
        instance.lastY = instance.yPos;
        instance.xPos = xPos;
        instance.yPos = yPos;

        // If any one of the mouse buttons are pressed and we are moving,
        // then we are dragging
        for (int i = 0; i < instance.mouseButtonPressed.length; i++) {
            instance.isDragging |= instance.mouseButtonPressed[i];
        }
    }

    public static void mouseButtonCallback(long windowId, int button, int action, int mods) {
        MouseListener instance = getInstance();
        if (action == GLFW_PRESS) {
            if (button < instance.mouseButtonPressed.length) {
                instance.mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < instance.mouseButtonPressed.length) {
                instance.mouseButtonPressed[button] = false;
                instance.isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long windowId, double xOffset, double yOffset) {
        MouseListener instance = getInstance();
        instance.scrollX = xOffset;
        instance.scrollY = yOffset;
    }

    public static void endFrame() {
        MouseListener instance = getInstance();
        instance.scrollX = 0;
        instance.scrollY = 0;
        instance.lastX = instance.xPos;
        instance.lastY = instance.yPos;
    }

    public static float getScrollX() {
        return (float) getInstance().scrollX;
    }

    public static float getScrollY() {
        return (float) getInstance().scrollY;
    }

    public static float getxPos() {
        return (float) getInstance().xPos;
    }

    public static float getyPos() {
        return (float) getInstance().yPos;
    }

    public static float getdX() {
        return (float) (getInstance().lastX - getInstance().xPos);
    }

    public static float getdY() {
        return (float) (getInstance().lastY - getInstance().yPos);
    }

    public static boolean isDragging() {
        return getInstance().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < getInstance().mouseButtonPressed.length)  {
            return getInstance().mouseButtonPressed[button];
        } else {
            return false;
        }
    }

    public static float getOrthoX() {
        float currentX = getxPos();
        currentX = ((currentX / Window.getWidth())) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);
        tmp.mul(Window.getActiveScene().getCamera().getInverseProjection()).mul(Window.getActiveScene().getCamera().getInverseView());

        currentX = tmp.x;
        return currentX;
    }

    public static float getOrthoY() {
        float currentY = Window.getHeight() - getyPos();
        currentY = ((currentY / Window.getHeight())) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);
        tmp.mul(Window.getActiveScene().getCamera().getInverseProjection()).mul(Window.getActiveScene().getCamera().getInverseView());

        currentY = tmp.y;
        return currentY;
    }
}
