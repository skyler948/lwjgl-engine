package engine.object;

import engine.component.Component;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;

public class GameObject {

    private HashMap<String, Component> components;

    private Vector3f position;
    private Quaternionf rotation;
    private float scale;

    private boolean active = true;

    private Matrix4f modelMatrix;

    public GameObject(Vector3f position, Quaternionf rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        modelMatrix = new Matrix4f();
        updateModelMatrix();

        components = new HashMap<>();
    }

    public GameObject(Vector3f position) {
        this(position, new Quaternionf(), 1.f);
    }

    public GameObject() {
        this(new Vector3f());
    }

    public void input() {
        for (Component component : components.values()) {
            if (!component.isActive()) continue;
            component.input();
        }
    }

    public void update() {
        for (Component component : components.values()) {
            if (!component.isActive()) continue;
            component.update();
        }
    }

    public void render() {
        for (Component component : components.values()) {
            if (!component.isActive()) continue;
            component.render();
        }
    }

    public void cleanUp() {
        for (Component component : components.values()) {
            component.cleanUp();
        }
    }

    private void updateModelMatrix() {
        modelMatrix.translationRotateScale(position, rotation, scale);
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        updateModelMatrix();
    }

    public void changePosition(Vector3f difference) {
        position.add(difference);
        updateModelMatrix();
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getZ() {
        return position.z;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
        updateModelMatrix();
    }

    public void changeRotation(Quaternionf difference) {
        rotation.add(difference);
        updateModelMatrix();
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setScale(float scale) {
        this.scale = scale;
        updateModelMatrix();
    }

    public void changeScale(float difference) {
        scale += difference;
        updateModelMatrix();
    }

    public float getScale() {
        return scale;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void addComponent(String name, Component component) {
        component.setParentObject(this);
        components.put(name, component);
    }

    public void removeComponent(String name) {
        components.get(name).setParentObject(null);
        components.remove(name);
    }

    public Component getComponent(String name) {
        return components.get(name);
    }

}
