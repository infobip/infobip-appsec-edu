import React from "react";
import { NavLink } from "react-router-dom";

function Navigation() {
  return (
    <div className="navigation">
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <div className="container">
          <NavLink className="navbar-brand" to="/">
            Bip Node Vulnerable App
          </NavLink>
          <div>
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <NavLink className="nav-link" to="/">
                  Home
                  <span className="sr-only">(current)</span>
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/xss">
                  XSS
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/xssfixed">
                  XSS Fixed
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/xxe">
                  XXE
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/xxefixed">
                  XXE Fixed
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/subscribe">
                  Template Injection
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/files">
                  Traversal
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/filesfixed">
                  Traversal fixed
                </NavLink>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Navigation;