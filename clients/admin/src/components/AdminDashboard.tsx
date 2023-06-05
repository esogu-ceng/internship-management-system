/** @format */

import { Link } from "react-router-dom";

import { routes } from "../constants";

function AdminDashboard() {
  const root_path : string | undefined = process.env.PUBLIC_URL
  return (
    <div className="dashboard-container">
      {routes.map(({ href, text }) => (
        <Link key={href} to={href} className="dashboard-card link">
          <h2>{text}</h2>
        </Link>
      ))}
    </div>
  );
}

export default AdminDashboard;