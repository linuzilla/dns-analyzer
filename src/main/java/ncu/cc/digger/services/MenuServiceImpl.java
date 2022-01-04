package ncu.cc.digger.services;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.commons.webdev.models.MenuEntry;
import ncu.cc.commons.webdev.utils.MenuBuilder;
import ncu.cc.digger.constants.RolesEnum;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.security.AppUserAuthentication;
import ncu.cc.digger.security.AppUserDetails;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.sessionbeans.MenuBarSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private AuthenticationService authenticationService;
    @Value(ValueConstants.CONTEXT_PATH)
    private String contextPath;
    @Autowired
    private MessageResourceService messageResourceService;
    @Autowired
    private MenuBuilder menuBuilder;

    private List<MenuBarSession.Entry> recursiveBuildMenu(AppUserAuthentication principal, String rootKey, String parentUrl, List<MenuEntry> menuEntries) {
        List<MenuBarSession.Entry> list = new ArrayList<>();

        for (MenuEntry entry: menuEntries) {
            String messageKey = rootKey + "." + entry.getName();
            String urlpath = parentUrl + entry.getUrlpath();

            boolean requireAuthorities = false;
            boolean haveAuthority = false;

            // FIXME
            if (entry.getAuthorities() != null && entry.getAuthorities().length > 0) {
                for (String authority : entry.getAuthorities()) {
                    if (authority != null && authority.length() > 0) {
                        requireAuthorities = true;

                        if (principal == null) {
                            break;
                        } else if (principal.hasRole(RolesEnum.toAuthority(authority))) {
                            haveAuthority = true;
                            break;
                        }
                    }
                }
            }

            if (principal != null && entry.getNegativeAuthorities() != null && entry.getNegativeAuthorities().length > 0) {
                for (String authority: entry.getNegativeAuthorities()) {
                    if (authority != null && authority.length() > 0) {
                        if (principal.hasRole(RolesEnum.toAuthority(authority))) {
                            requireAuthorities = true;
                            haveAuthority = false;
                            break;
                        }
                    }
                }
            }

            if (!requireAuthorities || haveAuthority) {
                MenuBarSession.Entry menuBarEntry = new MenuBarSession.Entry();
                menuBarEntry.setMessageKey(messageKey);
                menuBarEntry.setUrlpath(urlpath);
                menuBarEntry.setOrder(entry.getOrder());

                if (entry.getSubmenu() != null && entry.getSubmenu().size() > 0) {
                    List<MenuBarSession.Entry> entries = recursiveBuildMenu(principal, messageKey, parentUrl, entry.getSubmenu());

                    if (entries != null && entries.size() > 0) {
                        menuBarEntry.setSubentries(entries);
                    }
                }

                list.add(menuBarEntry);
            }
        }
        Collections.sort(list, Comparator.comparingInt(MenuBarSession.Entry::getOrder));
        return list;
    }

    @Override
    public void fillLocalizationMessage(List<MenuBarSession.Entry> menu) {
        for (MenuBarSession.Entry entry: menu) {
            try {
                entry.setMessage(messageResourceService.getMessage(entry.getMessageKey()));
            } catch (Exception e) {
                StackTraceUtil.print1(entry.getMessageKey() + " not apply");
                entry.setMessage(entry.getMessageKey());
            }
            if (entry.getSubentries() != null) {
                fillLocalizationMessage(entry.getSubentries());
            }
        }
    }

    @Override
    public List<MenuBarSession.Entry> buildMenu() {
        String parentUrl = contextPath != null && ! "/".equals(contextPath) ? contextPath : "";

        Authentication authentication = authenticationService.getAuthentication();

        if (authentication instanceof AppUserAuthentication) {
            var appUserAuthentication = (AppUserAuthentication) authentication;

            return recursiveBuildMenu(appUserAuthentication,
                    "menu", parentUrl, menuBuilder.getRootMenuEntry().getSubmenu());
        } else {
            return recursiveBuildMenu(null,
                    "menu", parentUrl, menuBuilder.getRootMenuEntry().getSubmenu());
        }
    }
}
