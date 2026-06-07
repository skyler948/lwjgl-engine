package engine.scene;

import engine.Engine;

public class GameScene extends Scene {

    public GameScene(Engine engine) {
        super(engine);
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
