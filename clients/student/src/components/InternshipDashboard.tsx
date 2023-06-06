import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import InternshipCard from './InternshipCard';
import { Internship } from '../types/InternshipType';
import { Company } from '../types/CompanyType';

function InternshipDashboard() {
  const root_path: string | undefined = process.env.PUBLIC_URL
  const [internships, setInternships] = useState<Internship[]>([]);
  const [companies, setCompanies] = useState<Company[]>([]);
  const [companyCount, setCompanyCount] = useState<number>(0);
  const [internshipCount, setInternshipCount] = useState<number>(0);

  useEffect(() => {
    fetch('/api/company')
      .then(response => response.json())
      .then((data: Company[]) => {
        setCompanies(data);
      })
      .catch(error => console.error(error));

    fetch('/api/company/count')
      .then(response => response.json())
      .then(count => {
        setCompanyCount(count);
      })
      .catch(error => console.error(error));

  }, []);

  useEffect(() => {
    fetch('/api/internship')
      .then(response => response.json())
      .then((data: Internship[]) => {
        setInternships(data);
      })
      .catch(error => console.error(error));

    fetch('/api/internship/count/all')
      .then(response => response.json())
      .then(count => {
        setInternshipCount(count);
      })
      .catch(error => console.error(error));

  }, []);


  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <Link to={`${root_path}/AllInternships`}>
          <h2>Tüm Stajlarım</h2>
          <p> {internshipCount} adet staj kaydı bulunmaktadır. </p>
        </Link>
      </div>
      <div className="dashboard-card">
        <h2>Firma Sayısı</h2>
        <p>{companyCount} adet firma bulunmaktadır.</p>
      </div>
      <div className="dashboard-card">
        <h2>Staj Bilgileri</h2>
      </div>
    </div>
  );
}

export default InternshipDashboard;