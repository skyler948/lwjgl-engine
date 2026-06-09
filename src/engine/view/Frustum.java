package engine.view;

import engine.Engine;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Frustum {

    private Engine engine;

    private Matrix4f projectionViewMatrix;
    private FrustumIntersection intersection;

    public Frustum(Engine engine) {
        this.engine = engine;

        projectionViewMatrix = new Matrix4f();
        intersection = new FrustumIntersection();
    }

    public void recalculate() {
        projectionViewMatrix.set(engine.getProjection().getProjectionMatrix());
        projectionViewMatrix.mul(engine.getCurrentScene().getCamera().getViewMatrix());

        intersection.set(projectionViewMatrix);
    }

    public boolean isInsideFrustum(Vector3f position, float radius) {
        return intersection.testSphere(position, radius);
    }

    public boolean isInsideFrustum(Vector3f min, Vector3f max) {
        return intersection.testAab(min, max);
    }

    public boolean isInsideFrustum(Vector3f point) {
        return intersection.testPoint(point);
    }

}
