import React from "react";
import { Link } from 'react-router-dom';

function Header() {
  return (
    <header>
      <div className="header-content">
        <h1 className="header-title">Staj Yönetim Sistemi</h1>
        <nav>
          <ul className="header-nav">
            <li>
              <Link to="facultysupervisor/company-list">Şirketler</Link>
            </li>
          </ul>
        </nav>
        <div className="header-buttons">
          <button className="header-button">Profil</button>
          <button className="header-button">Çıkış</button>
        </div>
      </div>
    </header>
  );
}

export default Header;
