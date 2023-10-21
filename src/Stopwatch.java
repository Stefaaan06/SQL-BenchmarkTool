package src;

public class Stopwatch {
    private static long startTime;
    private static long endTime;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static void stop() {
        endTime = System.currentTimeMillis();
    }

    public static long getElapsedTimeInMillis() {
        Stopwatch.stop();
        return endTime - startTime;
    }

    public static double getElapsedTimeInSeconds() {
        Stopwatch.stop();
        return (endTime - startTime) / 1000.0;
    }
}