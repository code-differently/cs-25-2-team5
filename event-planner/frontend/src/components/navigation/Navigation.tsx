import React from "react";
import { Link, useLocation } from "react-router-dom";
import './Navigation.css';
import { SignedIn, UserButton, useUser } from "@clerk/clerk-react";

const Navigation: React.FC = () => {
  const location = useLocation();
  const { isSignedIn } = useUser();

  const isLoginPage = location.pathname === '/login';
  const isSignUpPage = location.pathname === '/signup';

  // Determine which buttons to show
  const navButtons = () => {
    if (isSignedIn) {
      return (
        <>
          {!isLoginPage && !isSignUpPage && (
            <Link className="navigation-create-event-button" to="/createEvent">
              Create Event
            </Link>
          )}
          <SignedIn>
            <UserButton />
          </SignedIn>
        </>
      );
    } else {
      return (
        <>
          {!isLoginPage && (
            <Link className="navigation-login-button" to="/login">Login</Link>
          )}
          {!isSignUpPage && !isLoginPage && (
            <Link className="navigation-signup-button" to="/signup">Sign Up</Link>
          )}
        </>
      );
    }
  };

  return (
    <header className="header">
      <div className="navigation-logo">
        <Link to="/">
          <img src="/evynt-logo.png" alt="Evynt Logo" />
        </Link>
      </div>

      {!isLoginPage && (
        <ul className="navigation-top-menu">
          <li><Link to="/">Discover</Link></li>
          <li><Link to="/about">About Us</Link></li>
        </ul>
      )}

      <div className="auth-buttons">
        {navButtons()}

        {isLoginPage && (
          <Link className="navigation-home-button" to="/">
            Go Back Home
          </Link>
        )}
      </div>
    </header>
  );
};

export default Navigation;
