import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import React, { useEffect, useState } from "react";


function Header() {
  const root_path: string | undefined = process.env.PUBLIC_URL;
   const [institutionName, setInstitutionName] = useState("");
   
  useEffect(() => {
    const fetchInstitution = async () => {
      try {
        const response = await fetch(`/api/setting/institution_name`, {
          method: "GET",
        });
        const institutionValue = await response.json();
        setInstitutionName(institutionValue.value);
      } catch (error) {
        console.log(error);
      }
    };

    fetchInstitution();
  }, []);
  
  return (
    <header>
      <div className="header-content">
        <Link to={`${root_path}`} className="nav-link">
          <FontAwesomeIcon icon={faHome} className="header-home" />
        </Link>
        <p className="header-institution">{institutionName}</p>
        <button className="header-button">
          <Link to={`${root_path}/companies`} className="nav-link">
            Firmalar
          </Link>
        </button>
        <button className="header-login">
          <Link to={`${root_path}/login`} className="nav-link">
            Giriş Yap
          </Link>
        </button>
      </div>
    </header>
  );
}

export default Header;