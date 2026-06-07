package engine.scene;

import engine.Engine;
import engine.object.GameObject;

import java.util.ArrayList;

public abstract class Scene {

    private Engine engine;

    private ArrayList<GameObject> objects;

    public Scene(Engine engine) {
        this.engine = engine;

        objects = new ArrayList<>();
    }

    public abstract void input();

    public abstract void update();

    public abstract void render();

    public abstract void cleanUp();

    public void inputAllObjects() {
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
        for (GameObject object : objects) {
            if (!object.isActive()) continue;
            object.render();
        }
    }

    public void cleanUpAllObjects() {
        for (GameObject object : objects) {
            object.cleanUp();
        }
    }

    public void addObject(GameObject gameObject) {
        objects.add(gameObject);
    }

}
