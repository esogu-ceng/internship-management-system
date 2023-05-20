import React from "react";

function Header() {
  return (
    <header>
      <div className="header-content">
        <h1 className="header-title">Internship Management System</h1>
        <div className="header-buttons">
          <button className="header-button">Profile</button>
          <button className="header-button">Exit</button>
        </div>
      </div>
    </header>
  );
}

export default Header;