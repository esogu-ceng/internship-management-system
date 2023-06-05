import React from 'react';
import { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CompanyListContainer from './containers/CompanyListContainer/CompanyListContainer';
import InternshipDashboard from './components/InternshipDashboard';
import { User } from './types/UserType';
// Import other screen components

import Header from "./components/Header";
import Footer from "./components/Footer";


import AllInternships from "./components/AllInternships";


const App: React.FC = () => {
  const [user, setUser] = useState<User>();
  const [currentFacultySupervisorId, setcurrentFacultySupervisorId] = useState<number>(0);
  const root_path : string | undefined = process.env.PUBLIC_URL

  function getAuthUser() {
    fetch('/api/user/faculty-supervisor/auth', {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setUser(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  function getFacultySupervisorId(userId: number) {
    console.log(userId);
    fetch(`/api/facultySupervisor/byUserId/${userId}`, {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setcurrentFacultySupervisorId(data.id);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  useEffect(() => {
    getAuthUser();
  }, []);

  useEffect(() => {
    if (user) {
      getFacultySupervisorId(user.id);
    }
  }, [user]);

  return (
    <Router >
      <div>
        <Header />
        <div className="content-container">
          <Routes>
          ${currentFacultySupervisorId}
            <Route path={`${root_path}`} element={<InternshipDashboard />} />
            <Route path={`${root_path}/company-list`} element={<CompanyListContainer />} />
            <Route path={`${root_path}/AllInternship`} element={<AllInternships _facultySupervisorId={currentFacultySupervisorId} />} />
            {/* Add other routes for different screens */}
            <Route path={`${root_path}/default`} element={<div>Default Screen</div>} />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
};

export default App;