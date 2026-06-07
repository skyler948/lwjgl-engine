package engine.component;

import engine.object.GameObject;

public abstract class Component {

    private GameObject parentObject;
    private boolean active = true;

    public abstract void input();

    public abstract void update();

    public abstract void render();

    public abstract void cleanUp();

    public void setParentObject(GameObject parentObject) {
        this.parentObject = parentObject;
    }

    public GameObject getParentObject() {
        return parentObject;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

}
