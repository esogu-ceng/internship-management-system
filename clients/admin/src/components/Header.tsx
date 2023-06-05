import React from "react";
import { Link } from "react-router-dom";
function Header() {
  
  const root_path : string | undefined = process.env.PUBLIC_URL
    const handleLogout = () => {
    window.location.href = process.env.REACT_APP_API_BASE_URI + "logout";
  };

  return (
    <header>
      <div className="header-content">
        <h1 className="header-title">Staj Yönetim Sistemi</h1>
        <div className="header-buttons">
          <button className="header-button"  >
          <Link to={`${root_path}/profile`}>
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
