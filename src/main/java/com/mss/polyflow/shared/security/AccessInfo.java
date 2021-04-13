package com.mss.polyflow.shared.security;

import java.util.List;


public class AccessInfo {

    Integer roleId;

    String roleName;

    List<String> authorities;

    public Integer getRoleId() {
        return roleId;
    }

    public AccessInfo setRoleId(Integer roleId) {
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

    public List<String> getAuthorities() {
        return authorities;
    }


    public AccessInfo setAuthorities(List<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public String toString() {
        return "AccessInfo{" +
                   "roleId=" + roleId +
                   ", roleName='" + roleName + '\'' +
                   ", authorities=" + authorities +
                   '}';
    }



}
