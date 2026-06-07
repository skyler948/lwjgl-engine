package engine.object;

import engine.Engine;
import engine.shader.ShaderModuleData;
import engine.shader.ShaderProgram;
import engine.shader.UniformsMap;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ObjectRenderer {

    private Engine engine;

    private ShaderProgram shaderProgram;
    private UniformsMap uniformsMap;

    private ArrayList<GameObject> objects;

    public ObjectRenderer(Engine engine) {
        this.engine = engine;

        List<ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new ShaderModuleData("/shaders/objectVertex.glsl", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderModuleData("/shaders/objectFragment.glsl", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderModuleDataList);

        uniformsMap = new UniformsMap(shaderProgram.getProgramId());
        uniformsMap.createUniform("projectionMatrix");
        uniformsMap.createUniform("modelMatrix");

        objects = new ArrayList<>();
    }

    public void render() {
        shaderProgram.bind();

        uniformsMap.setUniform("projectionMatrix", engine.getProjection().getProjectionMatrix());

        for (GameObject object : objects) {
            if (!object.isActive()) continue;

            uniformsMap.setUniform("modelMatrix", object.getModelMatrix());

            object.render();
        }

        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanUp() {
        shaderProgram.cleanUp();
    }

    public void addGameObject(GameObject gameObject) {
        objects.add(gameObject);
    }

}
