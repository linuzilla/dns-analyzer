package ncu.cc.digger.services;

import ncu.cc.commons.utils.StringUtil;
import ncu.cc.commons.webdev.annotations.MenuItem;
import ncu.cc.commons.webdev.models.MenuEntry;
import ncu.cc.commons.webdev.utils.*;
import ncu.cc.digger.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Service
public class MenuBuilderImpl implements MenuBuilder {
    private static final Logger logger = LoggerFactory.getLogger(MenuBuilderImpl.class);

    private ClassFinder classFinder;
    private ControllerFinder controllerFinder;
    private MenuEntry rootMenuEntry;

    public MenuBuilderImpl() {
        controllerFinder = new ControllerFinderImpl(new ClassInJarFinderImpl());
    }

    private void scanned(String path, Method method) {
        if (method.isAnnotationPresent(MenuItem.class)) {
            logger.info("Found {} on {}:{}", path, method.getDeclaringClass().getName(), method.getName());
            MenuItem menuItem = method.getAnnotation(MenuItem.class);
            Assert.isTrue(! StringUtil.isNullOrEmpty(menuItem.value()), "Menu path should not be empty");
            String[] spited = menuItem.value().split("/");
            Assert.isTrue(spited.length > 0, menuItem.value() + ": incorrect Menu Path");

//            StackTraceUtil.print1("Len = " + spited.length + ", [" + spited[0] + "]");

            rootMenuEntry.addEntry(menuItem, spited, path, 0);
        }
    }

    @PostConstruct
    public void postConstruct() {
        rootMenuEntry = new MenuEntry(null, null);
        controllerFinder.componentScan(Constants.BASE_PACKAGE, this::scanned);
    }

//    @Override
//    public void handleRequest(HttpServletRequest request) {
//        String[] paths = request.getRequestURI().split("/");
//        int beginFrom = request.getContextPath().length() <= 1 ? 1 : 2;
//
//        List<MenuEntry> longestPrefixMatch = rootMenuEntry.findLongestPrefixMatch(
//                paths.length <= beginFrom ? new ArrayList<>() : Arrays.asList(paths).subList(beginFrom, paths.length)
//        );
//
////        for (MenuEntry menuEntry: longestPrefixMatch) {
////            StackTraceUtil.print1(menuEntry.getName());
////        }
//    }

    @Override
    public MenuEntry getRootMenuEntry() {
        return rootMenuEntry;
    }
}
