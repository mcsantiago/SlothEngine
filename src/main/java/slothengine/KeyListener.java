package slothengine;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private static KeyListener instance;
    private boolean[] keyPressed = new boolean[350];

    private KeyListener() {}

    public static KeyListener getInstance() {
        if (instance == null) {
            instance = new KeyListener();
        }
        return instance;
    }

    public static void keyCallback(long windowId, int key, int scancode, int action, int mods) {
        KeyListener instance = getInstance();

        if (action == GLFW_PRESS) {
            instance.keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            instance.keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int key) {
        if (key < getInstance().keyPressed.length) {
            return getInstance().keyPressed[key];
        } else {
            return false;
        }
    }
}
