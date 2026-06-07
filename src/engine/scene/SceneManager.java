package engine.scene;

public class SceneManager {

    private Scene scene;

    public SceneManager(Scene initialScene) {
        setScene(initialScene);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

}
