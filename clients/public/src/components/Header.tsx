import React from "react";

function Header() {
  return (
    <header>
      <div className="header-content">
        <h1 className="header-title">Staj Yönetim Sistemi</h1>
        <div className="header-buttons">
          <button className="header-button">Profil</button>
          <button className="header-button">Çıkış</button>
        </div>
      </div>
    </header>
  );
}

export default Header;