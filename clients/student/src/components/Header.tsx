import React from "react";
import { toast, ToastContainer } from "react-toastify";
import { Link } from 'react-router-dom';

function Header() {

  const root_path: string | undefined = process.env.PUBLIC_URL;
  const handleLogout = () => {
    window.location.href = process.env.REACT_APP_API_BASE_URI + "logout";
  };

  return (
    <header className="header">
      <div className="header-content">
        <Link to={`${root_path}/`}>
          <h1 className="header-title">Staj Yönetim Sistemi</h1>
        </Link>
        <nav className="navbar">
          <ul className="navbar-nav">
            <li className="nav-item">
              <Link to="/student/AllInternships" className="nav-link">Tüm Stajlarım</Link>
            </li>
            <li className="nav-item">
              <Link to="/internship-info" className="nav-link">Staj Bilgileri</Link>
            </li>
            <li className="nav-item">
              <Link to="/internship-application" className="nav-link">Staj Başvurusu</Link>
            </li>
            <li className="nav-item">
              <Link to="/internship-documents" className="nav-link">Staj Belgeleri</Link>
            </li>
            <li className="nav-item">
              <Link to="/" className="nav-link">Firmalar</Link>
            </li>
          </ul>
        </nav>
        <div className="header-buttons">
          <button className="header-button"  >
            <Link to={"student/profile"}>
              <h2>Profil</h2>
            </Link>
          </button>
          <button className="header-button" onClick={handleLogout}>Çıkış</button>
        </div>
      </div>
    </header>
  );
}

export default Header;