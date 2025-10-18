import React from "react";
import { Link, useLocation } from "react-router-dom";
import './Navigation.css';

const Navigation: React.FC = () => {
  const location = useLocation();
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
      
      {!isLoginPage && (
        <ul className="navigation-top-menu">
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <a href="#">Discover</a>
          </li>
          <li>
            <a href="#">About Us</a>
          </li>
        </ul>
      )}

      <div className="auth-buttons">
        {!isLoginPage && (
          <Link className="navigation-login-button" to="/login">
            Login
          </Link>
        )}
        {!isSignUpPage && !isLoginPage && (
          <Link className="navigation-signup-button" to="/signup">
            Sign Up
          </Link>
        )}
        {isLoginPage && (
          <Link className="navigation-home-button" to="/">
            {goBackHome ? 'Refresh Home' : 'Go Back Home'}
          </Link>
        )}
      </div>
    </header>
  );
};

export default Navigation;