import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import InternshipCard from './InternshipCard';
import { Internship } from '../types/InternshipType';
import { Company } from '../types/CompanyType';

function InternshipDashboard() {
  const [internships, setInternships] = useState<Internship[]>([]);
  const [companies, setCompanies] = useState<Company[]>([]);
  const root_path : string | undefined = process.env.PUBLIC_URL;

  useEffect(() => {
    fetch('/api/internship')
      .then(response => response.json())
      .then((data: Internship[]) => {
        setInternships(data);
      })
      .catch(error => console.error(error));

    fetch('/api/company')
      .then(response => response.json())
      .then((data: Company[]) => {
        setCompanies(data);
      })
      .catch(error => console.error(error));

  }, []);

  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
      <Link to={`${root_path}/AllInternships`}>
          <h2>Tüm Stajlarım</h2>
          <p>Toplam staj: {internships.length}</p>
        </Link>
      </div>
      <div className="dashboard-card">
        <h2>Firmalar</h2>
        <p>{companies.length} adet firma bulunmaktadır.</p>
      </div>
      <div className="dashboard-card">
        <h2>Staj Bilgileri</h2>
        {internships.map(internship => (
          <InternshipCard key={internship.id} internship={internship} />
        ))}
      </div>
    </div>
  );
}

export default InternshipDashboard;