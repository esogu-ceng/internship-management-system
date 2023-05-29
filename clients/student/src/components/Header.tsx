import React from 'react'
import { toast, ToastContainer } from 'react-toastify'

function Header() {
  const handleLogout = () => {
    window.location.href = process.env.REACT_APP_API_BASE_URI + 'logout'
  }

  return (
    <header>
      <div className="header-content">
        <h1 className="header-title">Staj Yönetim Sistemi</h1>
        <div className="header-buttons">
          <button className="header-button">Profil</button>
          <button className="header-button" onClick={handleLogout}>
            Çıkış
          </button>
        </div>
      </div>
    </header>
  )
}

export default Header;
