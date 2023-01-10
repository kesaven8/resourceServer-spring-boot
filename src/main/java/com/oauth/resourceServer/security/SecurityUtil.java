package com.oauth.resourceServer.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public  class SecurityUtil {

    public static String getClientId() {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return "test";
    }
}
