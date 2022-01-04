package ncu.cc.commons.utils;

import javax.validation.constraints.NotNull;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class StringUtil {
    private static final int [][]STRING_MASK_MATRIX = new int[][]{  // a**b, a***b, ab***c, ab***cd, ab****cd, ab*****cd, abc*****cd
                {  4, 1, 1},
                {  6, 2, 1},
                {  7, 2, 2},
                { 10, 3, 2}
        };

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static String hideStringWith(@NotNull String s, @NotNull String mask) {
        for (var i = STRING_MASK_MATRIX.length - 1; i >= 0; i--) {
            if (s.length() >= STRING_MASK_MATRIX[i][0]) {
                return s.substring(0, STRING_MASK_MATRIX[i][1])
                        + mask.repeat(s.length() - STRING_MASK_MATRIX[i][1] - STRING_MASK_MATRIX[i][2])
                        + s.substring(s.length() - STRING_MASK_MATRIX[i][2]);
            }
        }
        return mask.repeat(5);
    }
}
