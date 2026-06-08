package engine.scene;

import engine.Engine;
import engine.component.Mesh;
import engine.component.Texture;
import engine.object.GameObject;
import org.joml.Vector3f;

public class GameScene extends Scene {

    public GameScene(Engine engine) {
        super(engine);

        float[] positions = new float[]{
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
        };
        float[] textureCoordinates = new float[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
        };

        GameObject testObject = new GameObject(new Vector3f(0.25f, 0.25f, -2.f));
        testObject.addComponent("testTexture", new Texture("/textures/moss.png"));
        testObject.addComponent("testMesh", new Mesh(positions, textureCoordinates, indices));
        addObject(testObject);
    }

    @Override
    public void input() {
        inputAllObjects();
    }

    @Override
    public void update() {
        updateAllObjects();
    }

    @Override
    public void render() {
        renderAllObjects();
    }

    @Override
    public void cleanUp() {
        cleanUpAllObjects();
    }

}
