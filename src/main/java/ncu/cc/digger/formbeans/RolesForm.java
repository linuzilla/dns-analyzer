package ncu.cc.digger.formbeans;

import java.util.List;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class RolesForm {
    private List<String> roles = null;

    public RolesForm() {
    }

    public RolesForm(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
