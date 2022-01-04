package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.RolesEnum;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.constants.Views;
import ncu.cc.digger.formbeans.RolesForm;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.services.ReactiveCommandExecutor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(Routes.DEVEL)
public class DeveloperController {
    private static final List<GrantedAuthority> manageableRoles = List.of(
            RolesEnum.ROLE_SYSOP.getAuthority(),
            RolesEnum.ROLE_ADMIN.getAuthority()
    );

    private final AuthenticationService authenticationService;
    private final ReactiveCommandExecutor reactiveCommandExecutor;

    public DeveloperController(AuthenticationService authenticationService, ReactiveCommandExecutor reactiveCommandExecutor) {
        this.authenticationService = authenticationService;
        this.reactiveCommandExecutor = reactiveCommandExecutor;
    }

    @GetMapping(Routes.DEVEL_ROLE_MANIPULATE)
    public String roleManipulate(Model model) {
        List<String> myRoles = new ArrayList<>();

        Collection<? extends GrantedAuthority> authorities = authenticationService.getAuthentication().getAuthorities();

        manageableRoles.stream()
                .filter(authorities::contains)
                .forEach(x -> myRoles.add(x.toString()));

        model.addAttribute("allRoles", manageableRoles);
        model.addAttribute("roles", new RolesForm(myRoles));

        return Views.DEVEL_ROLES;
    }

    @PostMapping(Routes.DEVEL_ROLE_MANIPULATE)
    public String roleManipulatePost(
            @Valid @ModelAttribute("roles") RolesForm roles,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return Views.ERROR_DEFAULT;
        }

        var authentication = authenticationService.getAppUserAuthentication().get();

        manageableRoles.forEach(authentication::revokeAuthority);

        if (roles.getRoles() != null) {
            roles.getRoles().stream().filter(Objects::nonNull).forEach(
                    x -> authentication.addAuthority(RolesEnum.byName(x).getAuthority())
            );
        }

        return roleManipulate(model);
    }

    @GetMapping(Routes.DEVEL_THREADS)
    @ResponseBody
    public Collection<ReactiveCommandExecutor.ExecutionState> uncompleteCommands() {
        return this.reactiveCommandExecutor.dumpProcesses().values();
    }
}
