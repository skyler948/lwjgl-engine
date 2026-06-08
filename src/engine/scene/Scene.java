package engine.scene;

import engine.Engine;
import engine.object.GameObject;
import engine.object.ObjectRenderer;
import engine.view.Camera;

import java.util.ArrayList;

public abstract class Scene {

    private Engine engine;

    private Camera camera;

    private ArrayList<GameObject> objects;
    private ObjectRenderer objectRenderer;

    public Scene(Engine engine) {
        this.engine = engine;

        camera = new Camera(engine, 0.75f);

        objects = new ArrayList<>();
        objectRenderer = new ObjectRenderer(engine);
    }

    public abstract void input();

    public abstract void update();

    public abstract void render();

    public abstract void cleanUp();

    public void inputAllObjects() {
        camera.rotateCamera();
        camera.moveCameraFree();
        for (GameObject object : objects) {
            if (!object.isActive()) continue;
            object.input();
        }
    }

    public void updateAllObjects() {
        for (GameObject object : objects) {
            if (!object.isActive()) continue;
            object.update();
        }
    }

    public void renderAllObjects() {
        objectRenderer.render();
    }

    public void cleanUpAllObjects() {
        for (GameObject object : objects) {
            object.cleanUp();
        }
        objectRenderer.cleanUp();
    }

    public void addObject(GameObject gameObject) {
        objects.add(gameObject);
        objectRenderer.addGameObject(gameObject);
    }

    public Camera getCamera() {
        return camera;
    }

}
