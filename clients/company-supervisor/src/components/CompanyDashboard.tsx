import React from "react";
import { useNavigate } from "react-router-dom";

function CompanyDashboard() {
  const navigate  = useNavigate();
  const goToCompany = () => navigate('/company');
  const goToInternship = () => navigate('/internships');
  return (
    <div className="dashboard-container">
      <div className="dashboard-card" onClick={goToCompany}>
        <h2>Şirket Bilgileri</h2> {}
      </div>
      <div className="dashboard-card" onClick={goToInternship}> {}
        <h2>Stajlar</h2>
      </div>
    </div>
  );
}
export default CompanyDashboard;