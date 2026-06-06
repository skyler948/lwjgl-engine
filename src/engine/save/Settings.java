package engine.save;

import engine.display.Display;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Settings implements Savable {

    private static final String SETTINGS_NAME = "/settings.txt";

    private static final String[] DEFAULT_SETTINGS = {
            "width=1280",
            "height=720",
            "fps=60",
            "compat_profile=false",
            "use_xorg=false",
            "antialiasing=4"
    };

    private DirectoryManager directoryManager;

    private String settingsDirectory;

    private File settingsFile;

    private int width, height;
    private int fps;
    private boolean compatibleProfile, useXorg;
    private float fov;
    private float sensitivity;
    private byte antialiasing;

    public Settings(DirectoryManager directoryManager) {
        this.directoryManager = directoryManager;
        settingsDirectory = directoryManager.getRootUserDirectory() + SETTINGS_NAME;
    }

    @Override
    public void checkFile() {
        settingsFile = new File(settingsDirectory);

        try {
            if (settingsFile.createNewFile()) {
                createDefaults();
                System.out.println("New settings file created.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDefaults() {
        try {
            FileWriter writer = new FileWriter(settingsFile);

            for (String setting : DEFAULT_SETTINGS) {
                writer.write(setting);
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFile() {
        try {
            Scanner scanner = new Scanner(settingsFile);

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("=");
                data[1] = data[1].trim();

                switch (data[0].trim().toLowerCase(Locale.ROOT)) {
                    case "width":
                        width = Integer.parseInt(data[1]);
                        break;
                    case "height":
                        height = Integer.parseInt(data[1]);
                        break;
                    case "fps":
                        fps = Integer.parseInt(data[1]);
                        break;
                    case "compat_profile":
                        compatibleProfile = Boolean.parseBoolean(data[1]);
                        break;
                    case "use_xorg":
                        useXorg = Boolean.parseBoolean(data[1]);
                        break;
                    case "fov":
                        fov = Float.parseFloat(data[1]);
                        break;
                    case "sensitivity":
                        sensitivity = Float.parseFloat(data[1]);
                        break;
                    case "antialiasing":
                        antialiasing = Byte.parseByte(data[1]);
                        break;
                }
            }

            scanner.close();

            System.out.println("Settings file read.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveFile() {
        try {
            FileWriter writer = new FileWriter(settingsFile);

            writer.write("width=" + width + "\n");
            writer.write("height=" + height + "\n");
            writer.write("fps=" + fps + "\n");
            writer.write("use_xorg=" + useXorg + "\n");
            writer.write("fov=" + fov + "\n");
            writer.write("sensitivity=" + sensitivity + "\n");
            writer.write("sensitivity=" + antialiasing + "\n");

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDimensions(Display display, int width, int height) {
        this.width = width;
        this.height = height;

        saveFile();

        display.resized(this.width, this.height);
    }

    public int getFps() {
        return fps;
    }

    public boolean isCompatibleProfile() {
        return compatibleProfile;
    }

    public boolean isXorg() {
        return useXorg;
    }

    public float getFov() {
        return fov;
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public byte getAntialiasing() {
        return antialiasing;
    }

}
