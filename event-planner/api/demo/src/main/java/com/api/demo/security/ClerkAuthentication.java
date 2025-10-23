package com.api.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ClerkAuthentication extends AbstractAuthenticationToken {

    private final String userId;

    public ClerkAuthentication(String userId,boolean authenticated) {
        super(null);
        this.userId = userId;
        setAuthenticated(authenticated); 
    }

    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCredentials'");
    }

    @Override
    public Object getPrincipal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrincipal'");
    }

}
