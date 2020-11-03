package slothengine;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

  protected Camera camera;
  private boolean isRunning = false;
  protected List<GameObject> gameObjects = new ArrayList<>();

  public Scene() { }

  public abstract void update(float deltaTime);

  public void init() {}

  public void start() {
    for (GameObject go :
            gameObjects) {
      go.start();
    }
    isRunning = true;
  }

  public void addGameObject(GameObject go) {
    if (!isRunning) {
      gameObjects.add(go);
    } else {
      gameObjects.add(go);
      go.start();
    }
  }
}
