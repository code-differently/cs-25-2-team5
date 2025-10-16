import React from "react";
import { Link } from "react-router-dom";
import './Navigation.css';

const Navigation: React.FC = () => {
  return (
    <nav className="navigation-bar">
      <ul className="navigation-top-menu">
        <li>
          <a href="#">Discover</a>
        </li>
        <li>
          <a href="#">About Us</a>
        </li>
      </ul>
      
      <div className="auth-buttons">
        <Link to="/login">Login</Link>
        {/* <Link to="/register">Register</Link>  */} {/* Add this back when the Register */}
      </div>
    </nav>
  );
};

export default Navigation;