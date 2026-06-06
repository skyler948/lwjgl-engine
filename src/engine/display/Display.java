package engine.display;

import engine.save.DirectoryManager;
import engine.save.Settings;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;

import javax.swing.*;
import java.util.concurrent.Callable;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

    private final long WINDOW_HANDLE;

    private int width, height;
    private Callable<Void> resize;

    private DirectoryManager directoryManager;

    public Display(Callable<Void> resize) {
        this.resize = resize;
        directoryManager = new DirectoryManager();

        if (!directoryManager.isWindows() && getSettings().isXorg()) {
            glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11);
        }

        if (!glfwInit()) {
            JOptionPane.showMessageDialog(null, "Unable to initialize GLFW");
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

        if (getSettings().getAntialiasing() > 0) {
            glfwWindowHint(GLFW_SAMPLES, getSettings().getAntialiasing());
        }

        if (getSettings().isCompatibleProfile()) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }

        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (mode == null) {
            JOptionPane.showMessageDialog(null, "Unable to get GLFW video mode");
            throw new IllegalStateException("Unable to get GLFW video mode");
        }

        if (getSettings().getWidth() > 0 && getSettings().getHeight() > 0) {
            this.width = getSettings().getWidth();
            this.height = getSettings().getHeight();
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

            this.width = mode.width();
            this.height = mode.height();
        }

        WINDOW_HANDLE = glfwCreateWindow(width, height, "lwjgl engine!", NULL, NULL);
        if (WINDOW_HANDLE == NULL) {
            JOptionPane.showMessageDialog(null, "Failed to create GLFW window");
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwSetFramebufferSizeCallback(WINDOW_HANDLE, (d, w, h) -> resized(w, h));

        glfwSetErrorCallback((int errCode, long msgPtr) -> Logger.error("Error code [{}], msg [{}]", errCode, MemoryUtil.memUTF8(msgPtr)));

        glfwSetKeyCallback(WINDOW_HANDLE, (d, k, s, a, m) -> keyCallback(k, a));

        glfwMakeContextCurrent(WINDOW_HANDLE);

        glfwSwapInterval(getSettings().getFps() > 0 ? 0 : 1);

        glfwShowWindow(WINDOW_HANDLE);

        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];
        glfwGetFramebufferSize(WINDOW_HANDLE, arrWidth, arrHeight);
        this.width = arrWidth[0];
        this.height = arrHeight[0];
    }

    public void update() {
        glfwSwapBuffers(WINDOW_HANDLE);
    }

    public void resized(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            resize.call();
        } catch (Exception e) {
            throw new RuntimeException("Error resizing window:\n" + e);
        }
    }

    public void keyCallback(int key, int action) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(WINDOW_HANDLE, true);
        }
    }

    public boolean isKeyHeld(int key) {
        return glfwGetKey(WINDOW_HANDLE, key) == GLFW_PRESS;
    }

    public boolean displayShouldClose() {
        return glfwWindowShouldClose(WINDOW_HANDLE);
    }

    public void poll() {
        glfwPollEvents();
    }

    public void cleanUp() {
        glfwFreeCallbacks(WINDOW_HANDLE);
        glfwDestroyWindow(WINDOW_HANDLE);
        glfwTerminate();

        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        } else {
            throw new RuntimeException("Error freeing callbacks");
        }
    }

    public long getHandle() {
        return WINDOW_HANDLE;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DirectoryManager getDirectoryManager() {
        return directoryManager;
    }

    public Settings getSettings() {
        return directoryManager.getSettings();
    }

}
