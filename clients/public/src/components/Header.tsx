import React from "react";
import { Link } from "react-router-dom";

function Header() {
  return (
    <header>
      <div className="header-content">
        <h1 className="header-title">Staj Yönetim Sistemi
          <Link to="/companies" className="header-link-company">
            Firmalar
          </Link>
        </h1>
      </div>
    </header>
  );
}

export default Header;