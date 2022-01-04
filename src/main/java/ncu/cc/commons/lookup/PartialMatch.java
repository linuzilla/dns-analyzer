package ncu.cc.commons.lookup;

import java.util.Comparator;

public class PartialMatch<T extends ObjectStored<?>> implements Comparator<T> {
    public int compare(T arg0, T arg1) {
        String k1 = arg0.getKey().toLowerCase();
        String k2 = arg1.getKey().toLowerCase();
        int result = 0;

        if (k1.startsWith(k2)) {
            result = 0;
        } else {
            result = k1.compareTo(k2);
        }

        // System.out.printf("[%s] v.s. [%s] r=%d%n", k1, k2, result);

        return result;
    }

}
