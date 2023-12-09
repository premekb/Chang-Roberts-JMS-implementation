package ctu.fee.dsv.sem.util;

import java.util.Random;

public class RandomUtil {
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
}
