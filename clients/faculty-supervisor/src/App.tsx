import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CompanyListContainer from './containers/CompanyListContainer/CompanyListContainer';
import InternshipDashboard from './components/InternshipDashboard';
// Import other screen components

import Header from "./components/Header";
import Footer from "./components/Footer";


import AllInternships from "./components/AllInternships";




const App: React.FC = () => {
  const public_url : string = process.env.PUBLIC_URL
  return (
    <Router >
      <div>
        <Header />
        <div className="content-container">
          <Routes>
            <Route path={`${public_url}`} element={<InternshipDashboard />} />
            <Route path={`${public_url}/company-list`} element={<CompanyListContainer />} />
            <Route path={`${public_url}/AllInternships`}element={<AllInternships />} />
            {/* Add other routes for different screens */}
            <Route path={`${public_url}/default`} element={<div>Default Screen</div>} />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
};

export default App;

