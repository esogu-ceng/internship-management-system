import { Link } from "react-router-dom";

function AdminDashboard() {
  const root_path : string | undefined = process.env.PUBLIC_URL
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
        <h2>Fakülte Sorumluları</h2>
        {/* TODO */}
      </div>
      <div className="dashboard-card">
        <Link to={`${root_path}/setting`}>
          <h2>Ayarlar</h2>
        </Link>
        {/* TODO */}
      </div>
    </div>
  );
}

export default AdminDashboard;
