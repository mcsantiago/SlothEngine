package scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import components.Component;
import imgui.ImGui;
import renderer.Renderer;
import components.ComponentTypeAdapter;
import slothengine.Camera;
import slothengine.GameObject;
import slothengine.GameObjectTypeAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject = null;
    protected boolean levelLoaded = false;

    public Scene() { }

    public abstract void update(float deltaTime);

    public void init() {}

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
        if (isRunning) {
            go.start();
            this.renderer.add(go);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public void sceneImgui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    public void imgui() {

    }

    public void saveExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentTypeAdapter())
                .registerTypeAdapter(GameObject.class, new GameObjectTypeAdapter())
                .create();

        try {
            Files.writeString(Paths.get("tmp/level.json"), gson.toJson(gameObjects));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentTypeAdapter())
                .registerTypeAdapter(GameObject.class, new GameObjectTypeAdapter())
                .create();
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("tmp/level.json")));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        if (!inFile.equals("")) {
            int maxGoId = -1;
            int maxCompId = -1;
            try {
                GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
                for (GameObject obj : objs) {
                    addGameObject(obj);
                    for (Component c : obj.getComponents()) {
                        if (c.getUid() > maxCompId) {
                            maxCompId = c.getUid();
                        }
                    }
                    if (obj.getUid() > maxGoId) {
                        maxGoId = obj.getUid();
                    }
                }
                maxGoId++;
                maxCompId++;
                System.out.println(maxGoId);
                System.out.println(maxCompId);
                GameObject.init(maxGoId);
                Component.init(maxCompId);
                this.levelLoaded = true;
            } catch (JsonSyntaxException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
