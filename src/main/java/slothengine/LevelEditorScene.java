package slothengine;

import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
  public LevelEditorScene() {
  }

  @Override
  public void init() {
    super.init();
    this.camera = new Camera(new Vector2f());

    GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
    obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/mio-chan.png")));
    addGameObject(obj1);

    loadResources();
  }

  private void loadResources() {
    AssetPool.getShader("assets/shaders/default.glsl");
  }

  @Override
  public void update(float deltaTime) {
    if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
      camera.position.x += 100f * deltaTime;
    } else if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
      camera.position.x -= 100f * deltaTime;
    } else if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
      camera.position.y += 100f * deltaTime;
    } else if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
      camera.position.y -= 100f * deltaTime;
    }

    for (GameObject go : this.gameObjects) {
      go.update(deltaTime);
    }

    this.renderer.render();
  }
}
