import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CompanyListContainer from './containers/CompanyListContainer/CompanyListContainer';
import InternshipDashboard from './components/InternshipDashboard';
// Import other screen components

import Header from "./components/Header";
import Footer from "./components/Footer";


import AllInternships from "./components/AllInternships";


const App: React.FC = () => {
  const root_path : string | undefined = process.env.PUBLIC_URL
  return (
    <Router >
      <div>
        <Header />
        <div className="content-container">
          <Routes>
            <Route path={`${root_path}`} element={<InternshipDashboard />} />
            <Route path={`${root_path}/company-list`} element={<CompanyListContainer />} />
            <Route path={`${root_path}/AllInternships`}element={<AllInternships />} />
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