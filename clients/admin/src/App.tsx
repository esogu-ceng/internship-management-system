/** @format */

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import Header from "./components/Header";
import Footer from "./components/Footer";
import AdminDashboard from "./components/AdminDashboard";
import GetSettings from "./components/GetSettings";
import Students from "./components/Students";
import CompanySupervisors from "./components/CompanySupervisors";
import "./index.css";
import AdminProfile from "./components/AdminProfile";
import FacultySupervisors from "./components/FacultySupervisors";
import Companies from "./components/Companies";

import CompanySupervisorDetail from "./components/CompanySupervisorDetail";

function App() {
  return (
    <React.StrictMode>
      <ToastContainer />
      <Router>
        <Header />
        <div className="app-container">
          <Routes>
            <Route path={``} element={<AdminDashboard />} />
            <Route path={`settings`} element={<GetSettings />} />
            <Route
              path={`companySupervisors`}
              element={<CompanySupervisors />}
            />
            <Route path={`companySupervisors/:id`} element={<CompanySupervisorDetail />} />

            <Route
              path={`facultySupervisors`}
              element={<FacultySupervisors />}
            />
            <Route path={`profile`} element={<AdminProfile />} />
            <Route path={`students`} element={<Students />} />
            <Route path={`companies`} element={<Companies />} />
          </Routes>
        </div>
        <Footer />
      </Router>
    </React.StrictMode>
  );
}

export default App;
