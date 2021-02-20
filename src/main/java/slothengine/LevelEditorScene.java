package slothengine;

import components.Animation;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorScene extends Scene {

  private GameObject obj1;
  private GameObject obj2;

  public LevelEditorScene() {
  }

  @Override
  public void init() {
    super.init();

    loadResources();
    load();
    this.camera = new Camera(new Vector2f());

    if (this.levelLoaded) return;

    Spritesheet sprites = AssetPool.getSpritesheet("assets/spritesheets/spritesheet.png");
    obj1 = new GameObject("Object 1",
            new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), -1);
    SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
    obj1SpriteRenderer.setSprite(sprites.getSprite(0));
    Animation obj1Animation = new Animation();
    obj1Animation.setSpriteIndexStart(0);
    obj1Animation.setSpriteIndexEnd(4);
    obj1Animation.setSpriteFlipTime(0.2f);
    obj1Animation.setSprites(sprites);
    obj1.addComponent(obj1SpriteRenderer);
    obj1.addComponent(obj1Animation);
    addGameObject(obj1);

    obj2 = new GameObject("Object 2",
            new Transform(new Vector2f(500, 100), new Vector2f(256, 256)), 4);
    SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
    obj2SpriteRenderer.setSprite(sprites.getSprite(0));
    obj2.addComponent(obj2SpriteRenderer);
    Animation obj2Animation = new Animation();
    obj2Animation.setSpriteIndexStart(0);
    obj2Animation.setSpriteIndexEnd(4);
    obj2Animation.setSpriteFlipTime(0.2f);
    obj2Animation.setSprites(sprites);
    obj2.addComponent(obj2Animation);
    addGameObject(obj2);

    this.activeGameObject = obj1; // Temporary measure

  }

  private void loadResources() {
    AssetPool.getShader("assets/shaders/default.glsl");
    AssetPool.addSpritesheet("assets/spritesheets/spritesheet.png",
            new Spritesheet(AssetPool.getTexture("assets/spritesheets/spritesheet.png"), 16, 16, 26, 0, 0));
  }

  @Override
  public void update(float deltaTime) {
    for (GameObject go : this.gameObjects) {
      go.update(deltaTime);
    }

    this.renderer.render();
  }

  @Override
  public void imgui() {

  }
}
