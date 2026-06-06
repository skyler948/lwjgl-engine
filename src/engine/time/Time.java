package engine.time;

public class Time {

    private static long time = System.nanoTime();

    public static double deltaTime() {
        long now = System.nanoTime();
        double delta = (now - time) / 1000000000.0;
        time = now;
        return delta;
    }

}
