import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class Rand {
    public static double randRange(double from, double to) {
        return Math.random() * (to - from) + from;
    }

    public static double randRange(int from, int to) {
        return Math.random() * (to - from) + from;
    }

    public static int randInt(int range) {
        return ThreadLocalRandom.current().nextInt(0, range);
    }
    public static int randInt(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static String randString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i++) {
            string.append(chars.charAt(randInt(chars.length())));
        }
        return string.toString();
    }

    Double[] randomizeArray(boolean check, Double[] arr, int from, int to, double randomize_amount) {
        for (int i = from; i < to; i++) {
            arr[i] = arr[i] + randRange(-randomize_amount, randomize_amount);
            //    Todo: finish this

        }
        return arr;
    }
}
