package ncu.cc.commons.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ResourceUtil {
    public static String resourceToString(String resourceFile) {
//        StackTraceUtil.print1(resourceFile);
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(ResourceUtil.class.getClassLoader().getResourceAsStream(resourceFile), writer, StandardCharsets.UTF_8.name());
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
