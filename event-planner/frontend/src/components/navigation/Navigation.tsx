import React from "react";
import { Link, useLocation } from "react-router-dom";
import './Navigation.css';

const Navigation: React.FC = () => {
  const location = useLocation();
  const isLoginPage = location.pathname === '/login';

  return (
    <>
    <div className="navigation-logo">
      <a href="/">
        <img src="/evynt-logo.png" alt="Evynt Logo" />
      </a>
    </div>
    <nav className="navigation-bar">
        <ul className="navigation-top-menu">
          <li>
            <a href="#">Discover</a> {/* Directs you to the community events */}
          </li>
          <li>
            <a href="#">About Us</a> {/* Placeholder or can actually make an about us page */}
          </li>
        </ul>

        <div className="auth-buttons">
          {!isLoginPage && <Link to="/login">Login</Link>}
          {/* <Link to="/register">Register</Link>  */} {/* Add this back when the Register */}
        </div>
      </nav>
    </>
  );
};

export default Navigation;