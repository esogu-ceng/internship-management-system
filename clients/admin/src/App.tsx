/** @format */

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import Header from "./components/Header";
import Footer from "./components/Footer";
import AdminDashboard from "./components/AdminDashboard";
import GetSettings from "./components/GetSettings";
import CompanySupervisors from "./components/CompanySupervisors";
import "./index.css";
import AdminProfile from "./components/AdminProfile";

function App() {
  const root_path: string | undefined = process.env.PUBLIC_URL;
  return (
    <React.StrictMode>
      <ToastContainer />
      <Router>
        <Header />
        <div className="app-container">
          <Routes>
            <Route path={`${root_path}`} element={<AdminDashboard />} />
            <Route path={`${root_path}/settings`} element={<GetSettings />} />
            <Route
              path={`${root_path}/companySupervisors`}
              element={<CompanySupervisors />}
            />
            <Route path={`${root_path}/profile`} element={<AdminProfile />} />
          </Routes>
        </div>
        <Footer />
      </Router>
    </React.StrictMode>
  );
}

export default App;
