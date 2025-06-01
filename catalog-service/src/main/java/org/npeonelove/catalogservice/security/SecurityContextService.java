package org.npeonelove.catalogservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    // получение email из текущего Security Context
    public String getEmailFromSecurityContext() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getName();
    }

    // получение роли из текущего Security Context
    public String getRoleFromSecurityContext() {
        Authentication securityContext = SecurityContextHolder.getContext().getAuthentication();
        return securityContext.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("ROLE_USER");
    }
}
