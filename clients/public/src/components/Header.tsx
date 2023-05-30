import React from "react";
import { Link } from "react-router-dom";

function Header() {
  return (
    <header>
      <div className="header-content">
        <h1 className="header-title">Staj Yönetim Sistemi
        <a href="/companies" className="header-link-company">Firmalar</a>
        </h1>
      </div>
    </header>
  );
}

export default Header;