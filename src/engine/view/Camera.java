package engine.view;

import engine.Engine;
import engine.time.Time;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private static final float MOVE_SPEED = 4.f;

    private Engine engine;
    private float sensitivity;

    private Vector3f position;
    private Vector2f rotation;
    private Matrix4f viewMatrix;

    public Camera(Engine engine, float sensitivity) {
        this.engine = engine;
        this.sensitivity = sensitivity;

        position = new Vector3f();
        rotation = new Vector2f();
        viewMatrix = new Matrix4f();
    }

    public void updateViewMatrix() {
        viewMatrix.identity()
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .translate(-position.x, -position.y, -position.z);
    }

    public void rotateCamera() {
        rotation.add(
                engine.getDisplay().getMouseDifferencePosition().y * sensitivity * (float) engine.getTime(),
                engine.getDisplay().getMouseDifferencePosition().x * sensitivity * (float) engine.getTime());

        updateViewMatrix();
    }

    public void moveCameraFree() {
        float cos = (float) Math.cos(rotation.y);
        float cos90 = (float) Math.cos(rotation.y + (Math.PI / 2));

        if (engine.getDisplay().isKeyHeld(GLFW_KEY_W)) {
            position.add(-cos90 * (MOVE_SPEED * (float) engine.getTime()), 0, -cos * (MOVE_SPEED * (float) engine.getTime()));
        }
        if (engine.getDisplay().isKeyHeld(GLFW_KEY_S)) {
            position.add(cos90 * (MOVE_SPEED * (float) engine.getTime()), 0, cos * (MOVE_SPEED * (float) engine.getTime()));
        }

        if (engine.getDisplay().isKeyHeld(GLFW_KEY_A)) {
            position.add(-cos * (MOVE_SPEED * (float) engine.getTime()), 0, cos90 * (MOVE_SPEED * (float) engine.getTime()));
        }
        if (engine.getDisplay().isKeyHeld(GLFW_KEY_D)) {
            position.add(cos * (MOVE_SPEED * (float) engine.getTime()), 0, -cos90 * (MOVE_SPEED * (float) engine.getTime()));
        }

        if (engine.getDisplay().isKeyHeld(GLFW_KEY_SPACE)) {
            position.add(0, MOVE_SPEED * (float) engine.getTime(), 0);
        }
        if (engine.getDisplay().isKeyHeld(GLFW_KEY_LEFT_SHIFT)) {
            position.add(0, -MOVE_SPEED * (float) engine.getTime(), 0);
        }

        updateViewMatrix();
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
        updateViewMatrix();
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        updateViewMatrix();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation.set(rotation);
        updateViewMatrix();
    }

    public void setRotation(float x, float y) {
        rotation.set(x, y);
        updateViewMatrix();
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public float getSensitivity() {
        return sensitivity;
    }

}
