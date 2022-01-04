package ncu.cc.commons.webdev.utils;

import java.io.IOException;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface ClassFinder {
    Class<?>[]	findClassesByPackage(String packageName) throws IOException;
    // boolean		isa(Class<?> myclazz, Class<?> clazz);
}
