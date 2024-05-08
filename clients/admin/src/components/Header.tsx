// ** Router Dom Imports
import { Link } from "react-router-dom";

// ** Constants
import { routes } from "../constants";
import HamburgerMenu from "./hamburger-menu";

function Header() {
  const root_path: string | undefined = process.env.PUBLIC_URL;
  const handleLogout = () => {
    window.location.href = process.env.REACT_APP_API_BASE_URI + "logout";
  };

  return (
    <header>
      <div className="header-content">
        <Link to={`${root_path}/`}>
          <h1 className="header-title">Staj Yönetim Sistemi</h1>
        </Link>

        <div className="header-buttons ">
          {routes.map((route) => (
            <Link key={route.text} to={route.href} className="header-button">
              {route.text}
            </Link>
          ))}
          <button className="header-button">
            <Link to={`${root_path}/profile`}>
              <h2>Profil</h2>
            </Link>
          </button>
          <button className="header-button" onClick={handleLogout}>
            Çıkış
          </button>
        </div>
        <HamburgerMenu rootPath={root_path} handleLogout={handleLogout} />
      </div>
    </header>
  );
}

export default Header;
