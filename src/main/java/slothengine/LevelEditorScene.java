package slothengine;

import components.FontRenderer;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
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
  }

  @Override
  public void update(float deltaTime) {
    for (GameObject go : this.gameObjects) {
      go.update(deltaTime);
    }

    this.renderer.render();
  }
}
