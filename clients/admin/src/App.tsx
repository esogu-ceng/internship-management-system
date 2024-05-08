/** @format */

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import Header from "./components/Header";
import Footer from "./components/Footer";

import GetSettings from "./components/GetSettings";
import Students from "./components/Students";
import CompanySupervisors from "./components/CompanySupervisors";
import "./index.css";
import AdminProfile from "./components/AdminProfile";
import FacultySupervisors from "./components/FacultySupervisors";
import Companies from "./components/Companies";

import CompanySupervisorDetail from "./components/CompanySupervisorDetail";
import AdminDashboard from "./components/AdminDashboard";

const root_path: string | undefined = process.env.PUBLIC_URL;
function App() {
  return (
    <React.StrictMode>
      <ToastContainer />
      <Router>
        <Header />
        <div className="app-container">
          <Routes>
            <Route path={`${root_path}/`} element={<AdminDashboard />} />
            <Route path={`${root_path}/settings`} element={<GetSettings />} />
            <Route
              path={`${root_path}/companySupervisors`}
              element={<CompanySupervisors />}
            />
            <Route
              path={`${root_path}/companySupervisors/:id`}
              element={<CompanySupervisorDetail />}
            />

            <Route
              path={`${root_path}/facultySupervisors`}
              element={<FacultySupervisors />}
            />
            <Route path={`${root_path}/profile`} element={<AdminProfile />} />
            <Route path={`${root_path}/students`} element={<Students />} />
            <Route path={`${root_path}/companies`} element={<Companies />} />
          </Routes>
        </div>
        <Footer />
      </Router>
    </React.StrictMode>
  );
}

export default App;
