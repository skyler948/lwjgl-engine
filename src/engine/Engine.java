package engine;

import engine.display.Display;
import engine.scene.GameScene;
import engine.scene.Scene;
import engine.scene.SceneManager;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Engine {

    public static final int TARGET_TPS = 30;

    private boolean running = false;

    private Display display;
    private SceneManager sceneManager;

    private int targetFps;
    private int currentFps, currentTps;

    public Engine() {
        display = new Display(() -> {
            resize();
            return null;
        });
        this.targetFps = display.getSettings().getFps();

        init();
    }

    public void init() {
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        sceneManager = new SceneManager(new GameScene(this));
    }

    public void input() { // Use Time.deltaTime();
        getCurrentScene().input();
    }

    public void update() {
        getCurrentScene().update();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, display.getWidth(), display.getHeight());

        getCurrentScene().render();
    }

    private void run() {
        long initialTime = System.nanoTime();

        double updateTime = 1000000000.0 / TARGET_TPS;
        double renderTime = targetFps > 0 ? 1000000000.0 / targetFps : 0;

        double updateDelta = 0;
        double renderDelta = 0;

        long updateFrameTimer = 0, renderFrameTimer = 0;
        int ticks = 0, frames = 0;

        while (running && !display.displayShouldClose()) {
            display.poll();

            long now = System.nanoTime();
            updateDelta += (now - initialTime) / updateTime;
            renderDelta += (now - initialTime) / renderTime;

            updateFrameTimer += now - initialTime;
            renderFrameTimer += now - initialTime;

            if (targetFps <= 0 || renderDelta >= 1) {
                input();
            }

            if (updateDelta >= 1) {
                update();
                updateDelta--;
                ticks++;
            }
            if (updateFrameTimer >= 1000000000) {
                currentTps = ticks;
                updateFrameTimer = 0;
                ticks = 0;
            }

            if (targetFps <= 0 || renderDelta >= 1) {
                render();
                display.update();
                renderDelta--;
                frames++;
            }
            if (renderFrameTimer >= 1000000000) {
                currentFps = frames;
                renderFrameTimer = 0;
                frames = 0;
            }

            initialTime = now;
        }

        cleanUp();
    }

    public void start() {
        if (running) return;
        running = true;
        run();
    }

    public void resize() {
        if (display == null) return;
    }

    private void cleanUp() {
        display.cleanUp();
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.start();
    }

    public boolean isRunning() {
        return running;
    }

    public Display getDisplay() {
        return display;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public Scene getCurrentScene() {
        return sceneManager.getScene();
    }

    public int getTargetFps() {
        return targetFps;
    }

    public int getCurrentFps() {
        return currentFps;
    }

    public int getCurrentTps() {
        return currentTps;
    }

}
