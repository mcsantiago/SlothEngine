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

    int xOffset = 10;
    int yOffset = 10;

    float totalWidth = (float)(600 - xOffset * 2);
    float totalHeight = (float)(300 - yOffset * 2);
    float sizeX = totalWidth / 100.0f;
    float sizeY = totalHeight/ 100.0f;
    float padding = 0;

    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        float xPos = xOffset + (x * sizeX);
        float yPos = yOffset + (y * sizeY);

        GameObject go = new GameObject( "Obj " + x + " " + y,
                new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
        go.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
        this.addGameObject(go);
      }
    }

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
