import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CompanyListContainer from './containers/CompanyListContainer/CompanyListContainer';
// Import other screen components

import Header from './components/Header';
import Footer from './components/Footer';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <Header />
        <div className="content-container">
        <Routes>
          <Route path="/company-list" element={<CompanyListContainer />} />
          {/* Add other routes for different screens */}
          <Route path="/" element={<div>Default Screen</div>} />
        </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
};

export default App;
