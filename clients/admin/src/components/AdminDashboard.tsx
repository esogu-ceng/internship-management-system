import { Link } from "react-router-dom";

function AdminDashboard() {
  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <h2>Öğrenciler</h2>
        {/* TODO */}
      </div>
      <div className="dashboard-card">
        <h2>Şirket Sorumluları</h2>
        {/* TODO */}
      </div>
      <div className="dashboard-card">
        <Link to="/admin/facultysupervisor">
        <h2>Fakülte Sorumluları</h2>
        </Link>
      </div>
      <div className="dashboard-card">
        <h2>Ayarlar</h2>
        {/* TODO */}
      </div>
    </div>
  );
}

export default AdminDashboard;
