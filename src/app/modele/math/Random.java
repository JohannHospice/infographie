package app.modele.math;

public class Random {
    private static java.util.Random rand;

    static {
        rand = new java.util.Random();
    }

    public static int next(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
}
