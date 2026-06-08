package engine.component;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture extends Component {

    private static final byte CHANNELS_PER_PIXEL = 4;

    private int textureId;
    private String texturePath;

    private int width, height;

    public Texture(int width, int height, ByteBuffer buffer) {
        this.width = width;
        this.height = height;

        generateTexture(buffer);
    }

    public Texture(String texturePath) {
        this.texturePath = texturePath;

        try {
            PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(texturePath));

            width = decoder.getWidth();
            height = decoder.getHeight();

            ByteBuffer buffer = ByteBuffer.allocateDirect(CHANNELS_PER_PIXEL * width * height);
            decoder.decode(buffer, width * CHANNELS_PER_PIXEL, PNGDecoder.Format.RGBA);
            buffer.flip();

            generateTexture(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateTexture(ByteBuffer buffer) {
        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    @Override
    public void input() { }

    @Override
    public void update() { }

    @Override
    public void render() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    @Override
    public void cleanUp() {
        glDeleteTextures(textureId);
    }

}
