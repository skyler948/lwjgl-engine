package engine.save;

import java.io.File;

public class DirectoryManager {

    private static final String ROOT_NAME = "lwjglEngine";

    private String rootUserDirectory;
    private String userName;
    private boolean isWindows;

    private File rootUser;

    private Settings settings;

    public DirectoryManager() {
        getUserDirectory();

        settings = new Settings(this);
        settings.checkFile();
        settings.readFile();
    }

    private void getUserDirectory() {
        isWindows = System.getProperty("os.name").startsWith("Windows");
        userName = System.getProperty("user.name");

        if (isWindows) {
            rootUserDirectory = "C:/Users/" + userName + "/AppData/Roaming/" + ROOT_NAME;
        } else {
            rootUserDirectory = "/home/" + userName + "/.local/share/" + ROOT_NAME;
        }

        rootUser = new File(rootUserDirectory);

        if (rootUser.mkdirs()) {
            System.out.println("Creating user data directory...");
        }
    }

    public String getRootUserDirectory() {
        return rootUserDirectory;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isWindows() {
        return isWindows;
    }

    public Settings getSettings() {
        return settings;
    }

}
