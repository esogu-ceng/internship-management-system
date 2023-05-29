import React from "react";
import { Link } from "react-router-dom";

function InternshipDashboard() {
  const root_path : string | undefined = process.env.PUBLIC_URL
  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
      <Link to={`${root_path}/AllInternship`}>
          <h2>Staj Yapan Öğrenciler</h2>{/* TODO */}
        </Link>
      </div>
      <div className="dashboard-card">{/* TODO */}
        <h2>Geçmiş Stajlar</h2>
      </div>
      <div className="dashboard-card">{/* TODO */}
        <h2>Onay Bekleyen Staj Günlükleri</h2>
      </div>
    </div>
  );
}

export default InternshipDashboard;