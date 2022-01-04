package ncu.cc.commons.webdev.utils;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface ControllerFinder {
    void componentScan(String packageName, BiConsumer<String, Method> callback);
}
