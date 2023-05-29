import React from "react";
import { Link } from "react-router-dom";

function InternshipDashboard() {
  const root_path : string | undefined = process.env.PUBLIC_URL
  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <Link to={`${root_path}/AllInternships`}>
          <h2>Tüm Stajlarım</h2>
        </Link>
      </div>
      <div className="dashboard-card">
        <h2>Devam Eden Stajlarım</h2>
        {/* TODO */}
      </div>
      <div className="dashboard-card">
        {/* TODO */}
        <h2>Staj Başvurusu Yap</h2>
      </div>
      <div className="dashboard-card">
        {/* TODO */}
        <h2>Firmalar</h2>
      </div>
    </div>
  );
}

export default InternshipDashboard;
