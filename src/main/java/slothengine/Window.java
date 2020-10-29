package slothengine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import util.Time;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private int width, height;
  private final String title;
  private long windowId;
  public float r, g, b, a;

  private static Window instance = null;

  private static Scene activeScene = null;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Oni-chan";
  }

  public static void changeScene(int scene) {
    switch (scene) {
      case 0:
        activeScene = new LevelEditorScene();
        break;
      case 1:
        activeScene = new LevelScene();
        break;
      default:
        throw new IndexOutOfBoundsException("Scene not loaded");
    }
  }

  public static Window get() {
    if (Window.instance == null) {
      Window.instance = new Window();
    }

    return Window.instance;
  }

  public void run() {
    System.out.println("Hello LWJGL " + Version.getVersion() + "!");

    init();
    loop();

    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(windowId);
    glfwDestroyWindow(windowId);

    // Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  private void init() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    // Create the window
    windowId = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
    if (windowId == NULL) throw new RuntimeException("Failed to create the GLFW window");

    glfwSetCursorPosCallback(windowId, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(windowId, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(windowId, MouseListener::mouseScrollCallback);
    glfwSetKeyCallback(windowId, KeyListener::keyCallback);

    // Get the thread stack and push a new frame
    try (MemoryStack stack = stackPush()) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(windowId, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
          windowId, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(windowId);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(windowId);

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();

    Window.changeScene(0);
  }

  private void loop() {
    float beginTime = Time.getTime();
    float endTime = Time.getTime();
    float deltaTime = -1.0f;

    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while (!glfwWindowShouldClose(windowId)) {
      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();

      glClearColor(r, g, b, a);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      if (deltaTime >= 0) {
        activeScene.update(deltaTime);
      }

      glfwSwapBuffers(windowId); // swap the color buffers

      // Input
      if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
        glfwSetWindowShouldClose(windowId, true);
      }

      endTime = Time.getTime();
      deltaTime = endTime - beginTime;
      beginTime = endTime;
    }
  }
}
