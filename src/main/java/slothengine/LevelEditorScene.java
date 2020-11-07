package slothengine;

import components.Animation;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

  private GameObject obj1;

  public LevelEditorScene() {
  }

  @Override
  public void init() {
    super.init();
    loadResources();
    this.camera = new Camera(new Vector2f());

    Spritesheet sprites = AssetPool.getSpritesheet("assets/spritesheets/spritesheet.png");

    obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
    obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
    obj1.addComponent(new Animation(0, 4, 0.2f, sprites));
    addGameObject(obj1);

  }

  private void loadResources() {
    AssetPool.getShader("assets/shaders/default.glsl");
    AssetPool.addSpritesheet("assets/spritesheets/spritesheet.png",
            new Spritesheet(AssetPool.getTexture("assets/spritesheets/spritesheet.png"), 16, 16, 26, 0, 0));
  }

  @Override
  public void update(float deltaTime) {
    obj1.transform.position.x += 100f * deltaTime;

    for (GameObject go : this.gameObjects) {
      go.update(deltaTime);
    }

    this.renderer.render();
  }
}
