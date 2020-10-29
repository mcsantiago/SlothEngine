package slothengine;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {
  private boolean changingScene;
  private float timeToChangeScene = 2f;

  public LevelEditorScene() {
    System.out.println("Loaded Level Editor Scene");
    Window window = Window.get();
    window.r = 1.0f;
    window.g = 1.0f;
    window.b = 1.0f;
    window.a = 1.0f;
  }

  @Override
  public void update(float deltaTime) {
    Window window = Window.get();
    if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
      changingScene = true;
    }

    if (changingScene && timeToChangeScene > 0) {
      timeToChangeScene -= deltaTime;
      window.r -= deltaTime * 5.0f;
      window.g -= deltaTime * 5.0f;
      window.b -= deltaTime * 5.0f;
      window.a -= deltaTime * 5.0f;
    }
    else if (changingScene) {
      Window.changeScene(1);
    }
  }
}
