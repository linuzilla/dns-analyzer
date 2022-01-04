package ncu.cc.commons.webdev.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ClassInJarFinderImpl implements ClassFinder {
    private static final Logger logger = LoggerFactory.getLogger(ClassInJarFinderImpl.class);
    private static String base;

    static {
        base = ClassInJarFinderImpl.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    @Override
    public Class<?>[] findClassesByPackage(String packageName) throws IOException {
        logger.info("looking for package: " + packageName);
        List<Class<?>> classes = new ArrayList<>();

        String path = packageName.replace('.', '/');

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        String antStylePattern = "classpath*:/" + path + "/**";
        logger.info("AntStylePattern = {}", antStylePattern);
        Resource[] resources = resolver.getResources(antStylePattern);

        logger.info("number of resources: {}", resources.length);

        for (Resource resource : resources) {
            if (resource.getURL() != null) {
                String file = resource.getURL().getFile();

                if (file.startsWith(base)) {
                    file = file.substring(base.length());

                    if (file.endsWith(".class")) {
                        try {
                            String classFile = file.replace('/', '.');

                            Class<?> aClass = Class.forName(classFile.substring(0, classFile.length() - 6));
                            logger.debug("apply: {}", aClass.getName());

                            classes.add(aClass);
                        } catch (ClassNotFoundException e) {
                        }
                    }
                }
            }
        }
        return classes.toArray(new Class[classes.size()]);
    }
}
