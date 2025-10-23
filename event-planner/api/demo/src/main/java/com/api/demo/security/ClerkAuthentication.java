// package com.api.demo.security;

// import org.springframework.security.authentication.AbstractAuthenticationToken;

// public class ClerkAuthentication extends AbstractAuthenticationToken {

//   private final String userId;

//   public ClerkAuthentication(String userId, boolean authenticated) {
//     super(null);
//     this.userId = userId;
//     setAuthenticated(authenticated);
//   }

//   @Override
//   public Object getCredentials() {
//     return null;
//   }

//   @Override
//   public Object getPrincipal() {
//     return userId;
//   }
// }
