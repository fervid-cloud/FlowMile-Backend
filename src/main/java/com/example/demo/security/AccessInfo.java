package com.example.demo.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;


public class AccessInfo {

    String roleId;

    String roleName;

    Collection< ? extends GrantedAuthority> grantedAuthorities;

    public String getRoleId() {
        return roleId;
    }

    public AccessInfo setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public AccessInfo setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public AccessInfo setGrantedAuthorities(
        Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
        return this;
    }

    @Override
    public String toString() {
        return "AccessInfo{" +
                   "roleId='" + roleId + '\'' +
                   ", roleName='" + roleName + '\'' +
                   ", grantedAuthorities=" + grantedAuthorities +
                   '}';
    }
}
