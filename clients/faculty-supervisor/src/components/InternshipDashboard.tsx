import React from "react";
import { Link } from "react-router-dom";

function InternshipDashboard() {
  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <Link to="/facultysupervisor/AllInternships">
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