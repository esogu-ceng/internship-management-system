import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import InternshipCard from './InternshipCard';
import { Internship } from '../types/InternshipType';
import { Student } from '../types/StudentType';
import { Company } from '../types/CompanyType';

function InternshipDashboard() {
  const root_path : string | undefined = process.env.PUBLIC_URL
  const [internships, setInternships] = useState<Internship[]>([]);
  const [studentCount, setStudentCount] = useState<number>(0);
  const [companyCount, setCompanyCount] = useState<number>(0);
  const [approvedInternships, setApprovedInternships] = useState<number>(0);
  const [rejectedInternships, setRejectedInternships] = useState<number>(0);
  const [pendingInternships, setPendingInternships] = useState<number>(0);
  useEffect(() => {
    fetch('/api/internship')
      .then(response => response.json())
      .then((data: Internship[]) => {
        setInternships(data);
        data.map(internship => ( 
          <InternshipCard key={internship.id} internship={internship} />
        ))
      })
      .catch(error => console.error(error));

    fetch('/api/student')
      .then(response => response.json())
      .then((data: Student[]) => {
        setStudentCount(data.length);
      })
      .catch(error => console.error(error));

    fetch('/api/company')
      .then(response => response.json())
      .then((data: Company[]) => {
        setCompanyCount(data.length);
      })
      .catch(error => console.error(error));
      
    fetch('/api/company/count')
    .then(response => response.json())
    .then(count => {
      setCompanyCount(count);
    })
    .catch(error => console.error(error));
    
    fetch('/api/internship/count/rejected')
    .then(response => response.json())
    .then(count => {
      setRejectedInternships(count);
    })
    .catch(error => console.error(error));

    fetch('/api/internship/count/pending')
    .then(response => response.json())
    .then(count => {
    setPendingInternships(count);
     })
  .catch(error => console.error(error));
  }, []);

  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <Link to={`${root_path}/AllInternships`}>
          <h2>Staj Yapan Öğrenciler</h2>
          <p>Toplam öğrenci: {studentCount}</p>
        </Link>
      </div>
      <div className="dashboard-card">
        <h2>Staj Başvuruları</h2>
        <p>{internships.length} adet başvuru bulundu.</p>
        {Array.isArray(internships) && internships.map(internship => (
          <InternshipCard key={internship.id} internship={internship} />
        ))}
      </div>
      <div className="dashboard-card">
        <h2>Onaylanan Staj Başvurusu </h2>
        <p>{approvedInternships} adet staj başvurusu onaylandı.</p>
      </div>
      <div className="dashboard-card">
        <h2>Reddedilmiş Staj Başvurusu </h2>
        <p>{rejectedInternships} adet staj başvurusu reddedildi.</p>
      </div>
      <div className="dashboard-card">
        <h2>Onay Bekleyen Staj Başvurusu </h2>
        <p>{pendingInternships} adet staj başvurusu onay beklemektedir.</p>
      </div>
      <div className="dashboard-card">
        <h2>Firma Sayısı</h2>
        <p>{companyCount} adet firma bulunmaktadır.</p>
      </div>
    </div>
  );
};

export default InternshipDashboard;