package scenes;

import components.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import slothengine.*;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private Spritesheet sprites;
    private Spritesheet decorationsAndBlocks;

    public LevelEditorScene() {
    }

    @Override
    public void init() {
        super.init();

        loadResources();
        sprites = AssetPool.getSpritesheet("assets/spritesheets/spritesheet.png");
        decorationsAndBlocks = AssetPool.getSpritesheet("assets/spritesheets/decorationsAndBlocks.png");
        this.camera = new Camera(new Vector2f());

        if (this.levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        GameObject obj1 = new GameObject("Object 1",
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
        obj1.addComponent(new RigidBody());
        addGameObject(obj1);

        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(500, 100), new Vector2f(256, 256)), 4);
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        obj2SpriteRenderer.setSprite(sprites.getSprite(0));
        Animation obj2Animation = new Animation();
        obj2Animation.setSpriteIndexStart(0);
        obj2Animation.setSpriteIndexEnd(4);
        obj2Animation.setSpriteFlipTime(0.2f);
        obj2Animation.setSprites(sprites);
        obj2.addComponent(obj2SpriteRenderer);
        obj2.addComponent(obj2Animation);
        obj2.addComponent(new RigidBody());
        addGameObject(obj2);

        this.activeGameObject = obj1; // Temporary measure
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/spritesheets/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/spritesheets/spritesheet.png"),
                        16, 16, 26, 0, 0));
        AssetPool.addSpritesheet("assets/spritesheets/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/spritesheets/decorationsAndBlocks.png"),
                        16, 16, 81, 0, 0));
    }

    @Override
    public void update(float deltaTime) {
        MouseListener.getOrthoX();

        for (GameObject go : this.gameObjects) {
            go.update(deltaTime);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor Scene");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < decorationsAndBlocks.size(); i++) {
            Sprite sprite = decorationsAndBlocks.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                System.out.println("Button " + i + " clicked");
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonx2 = lastButtonX2 + itemSpacing.x + spriteWidth;

            if (i + 1 < decorationsAndBlocks.size() && nextButtonx2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
