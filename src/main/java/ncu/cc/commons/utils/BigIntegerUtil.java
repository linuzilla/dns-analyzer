package ncu.cc.commons.utils;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class BigIntegerUtil {
    private static long RAMDOM_BASE = 100_000_000L;

    public static long generateRandomUniqueLong(long number) {
        return 1_000_000_000_000_000_000L +
                number * RAMDOM_BASE + RandomUtil.nextLong(RAMDOM_BASE);
    }
}
