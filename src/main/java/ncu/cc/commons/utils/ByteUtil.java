package ncu.cc.commons.utils;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ByteUtil {
    public static final byte TRUE = (byte) 1;
    public static final byte FALSE = (byte) 0;

    public static byte byteBoolean(Boolean value) {
        return value != null && value ? TRUE : FALSE;
    }
}
