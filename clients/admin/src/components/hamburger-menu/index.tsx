// ** React & RRD Imports
import { FC, useState } from "react";
import { Link } from "react-router-dom";

// ** Module css
import styles from "./hamburger.module.css";

// ** Constants
import { routes } from "../../constants";

// ** Ant Design Imports
import { CloseOutlined, MenuFoldOutlined } from "@ant-design/icons";

interface Props {
  handleLogout: () => void;
  rootPath: string;
}

const HamburgerMenu: FC<Props> = (props) => {
  const { handleLogout, rootPath } = props;

  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <div className={styles.container}>
      {/* ICON */}
      <div
        className={styles.icon}
        title="Menüyü aç"
        onClick={() => setIsMenuOpen(true)}
      >
        <MenuFoldOutlined />
      </div>
      {/* MENU */}
      {isMenuOpen ? (
        <div className={styles.outerMenu} onClick={() => setIsMenuOpen(false)}>
          <div
            className={styles.innerMenu}
            onClick={(e) => e.stopPropagation()}
          >
            {/* Close Icon */}
            <div
              className={styles.closeIcon}
              onClick={() => setIsMenuOpen(false)}
            >
              <CloseOutlined />
            </div>

            {/* Nav Items */}
            <ul className={styles.navList}>
              {routes.map(({ href, text }) => (
                <li onClick={() => setIsMenuOpen(false)}>
                  <Link to={href} className={styles.listItem}>
                    {text}
                  </Link>
                </li>
              ))}
              {/* PROFILE & LOGOUT ITEMS */}
              <li onClick={() => setIsMenuOpen(false)}>
                <Link to={`${rootPath}/profile`} className={styles.listItem}>
                  Profile
                </Link>
              </li>
              <li
                onClick={() => {
                  setIsMenuOpen(false);
                  handleLogout();
                }}
              >
                <Link to={`${rootPath}/profile`} className={styles.listItem}>
                  Çıkış
                </Link>
              </li>
            </ul>
          </div>
        </div>
      ) : null}
    </div>
  );
};

export default HamburgerMenu;
