import React from "react";
import { Link } from "react-router-dom";

function Header() {
  const handleLogout = () => {
    window.location.href = process.env.REACT_APP_API_BASE_URI + "logout";
  };

  return (
    <header className="header">
      <div className="header-content">
        <Link to="facultysupervisor" className="header-title">Staj Yönetim Sistemi</Link>
        <nav className="navbar">
          <ul className="navbar-nav">
            <li className="nav-item">
              <Link to="facultysupervisor/company-list" className="nav-link">Şirketler</Link>
            </li>
            <li className="nav-item">
              <Link to="facultysupervisor/AllInternships" className="nav-link">Stajlar</Link>
            </li>
            <li className="nav-item">
              <Link to="facultysupervisor/student-list" className="nav-link">Fakülte Öğrencileri</Link>
            </li>
          </ul>
        </nav>
        <div className="header-buttons">
          <button className="header-button"  >
            <Link to={"facultysupervisor/profile"}>
              <h2>Profil</h2>
            </Link>
          </button>
          <button className="header-button" onClick={handleLogout}>
            Çıkış
          </button>
        </div>
      </div>
    </header>
  );
}

export default Header;