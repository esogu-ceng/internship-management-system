import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';

const InternshipApplication = () => {
  const [companies, setCompanies] = useState<Company[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    fetch('/api/company/getAllCompanies') // Assuming this endpoint returns all companies
      .then(response => response.json())
      .then(data => {
        
        setCompanies(data.content); // Assuming the companies are in the `content` property
        setLoading(false);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  const handleApply = (companyId: number) => {
    // Implement your apply logic here, such as opening a modal or navigating to apply page
    console.log('Apply button clicked for company ID:', companyId);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>List of Companies</h1>
      <div className="grid grid-cols-3 gap-4">
        {companies.map(company => (
          <div key={company.id} className="bg-white rounded-lg p-6 shadow-md">
            <h2 className="text-xl font-bold">{company.name}</h2>
            <p className="text-gray-600">{company.scope}</p>
            <p className="mt-4">{company.description}</p>
            <button className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onClick={() => handleApply(company.id)}>Apply</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default InternshipApplication;
