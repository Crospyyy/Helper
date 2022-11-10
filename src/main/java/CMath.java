@SuppressWarnings("unused")
public class CMath {
    public static double round(double number, int decimalPlaces) {
        return Math.round(number * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
    }


    public static double progress(double current, double total) {
        return current / total;
    }

    public static long timeUntilFinish(double current, double total, long timeDifference) {
        double progress = progress(current, total);

        return (long) (timeDifference * (1 / progress - 1));
//        System.out.println(1000*(1.1 / 0.1 - 1));

    }

    public static long finishTime(double current, double total, long timeDifference) {
        long timeUntilFinish = timeUntilFinish(current, total, timeDifference);
        return System.currentTimeMillis() + timeUntilFinish;
    }

    public static long finishTime(double current, double before, double goal, long timeDifference) {
        double total = goal - before;
        System.out.println("total: " + total);
//        System.out.println("current: " + current);
        System.out.println("current - before: " + (current - before));
        System.out.println("time difference: " + timeDifference);
        return finishTime(current - before, total, timeDifference);
    }
}
