import React from "react";
import { Link, useLocation } from "react-router-dom";
import './Navigation.css';
import { SignedIn, UserButton, useAuth } from "@clerk/clerk-react";

const Navigation: React.FC = () => {
  const location = useLocation();
  const { isSignedIn } = useAuth();
  const isAuthenticated = isSignedIn;

  const isLoginPage = location.pathname === '/login';
  const isSignUpPage = location.pathname === '/signup';
  const goBackHome = location.pathname === '/';

  return (
    <header className="header">
      <div className="navigation-logo">
        <Link to="/">
          <img src="/evynt-logo.png" alt="Evynt Logo" />
        </Link>
      </div>
      
      {/* Top Menu - Only show when not on auth pages */}
      {!isLoginPage && !isSignUpPage && (
        <ul className="navigation-top-menu">
          <li>
            <Link to="/">Discover</Link>
          </li>
          <li><Link to="/about">About Us</Link></li>
          {/* Only show Invitations when authenticated */}
          {isAuthenticated && (
            <li><Link to="/invitations">Invitations</Link></li>
          )}
        </ul>
      )}

      {/* Auth Buttons Section */}
      <div className="auth-buttons">
        {isAuthenticated && !isLoginPage && !isSignUpPage && (
          <Link className="navigation-create-event-button" to="/createEvent">
            Create Event
          </Link>
        )}
        
        {!isAuthenticated && !isLoginPage && !isSignUpPage && (
          <>
            <Link className="navigation-login-button" to="/login">
              Login
            </Link>
            <Link className="navigation-signup-button" to="/signup">
              Sign Up
            </Link>
          </>
        )}
        {isLoginPage && (
          <Link className="navigation-home-button" to="/">
            {goBackHome ? 'Refresh Home' : 'Go Back Home'}
          </Link>
        )}
        <SignedIn>
          <UserButton />
        </SignedIn>
      </div>
    </header>
  );
};

export default Navigation;