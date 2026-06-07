package engine.component;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh extends Component {

    private int numVertices;
    private int vaoId;
    private List<Integer> vboIdList;

    public Mesh(float[] vertices, int[] indices) {
        this.numVertices = vertices.length;
        vboIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Vertices
        int vboId = glGenBuffers();
        vboIdList.add(vboId);

        FloatBuffer verticesBuffer = MemoryUtil.memCallocFloat(vertices.length);
        verticesBuffer.put(0, vertices);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Indices
        vboId = glGenBuffers();
        vboIdList.add(vboId);

        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
        indicesBuffer.put(0, indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        MemoryUtil.memFree(verticesBuffer);
        MemoryUtil.memFree(indicesBuffer);
    }

    @Override
    public void input() { }

    @Override
    public void update() { }

    @Override
    public void render() {
        glBindVertexArray(vaoId);
        glDrawElements(GL_TRIANGLES, numVertices, GL_UNSIGNED_INT, 0);
    }

    @Override
    public void cleanUp() {
        vboIdList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getVaoId() {
        return vaoId;
    }

}
