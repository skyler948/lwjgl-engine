package engine.view;

import engine.Engine;
import org.joml.Matrix4f;

public class Projection {

    public static final float Z_FAR = 1000.f;
    public static final float Z_NEAR = 0.01f;

    private Engine engine;
    private float fov;

    private Matrix4f projectionMatrix;

    public Projection(Engine engine, float fov) {
        this.engine = engine;
        this.fov = (float) Math.toRadians(Math.clamp(fov, 1.f, 179.f));

        projectionMatrix = new Matrix4f();

        updateProjectionMatrix();
    }

    public void updateProjectionMatrix() {
        projectionMatrix.setPerspective(fov, engine.getDisplay().getRatio(), Z_NEAR, Z_FAR);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public float getFov() {
        return fov;
    }

}
