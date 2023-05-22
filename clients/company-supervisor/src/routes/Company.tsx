import React from 'react';
import { Header } from '../components/Header';
import CompanyDashboard from '../components/CompanyDashboard';

export const Company = () => {
  return (
    <div className="flex flex-col justify-between items-center">
      {/* <Header/> */}
      <CompanyDashboard />
      <div>Company</div>
    </div>
  );
};
