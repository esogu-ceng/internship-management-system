import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import InternshipCard from './InternshipCard';
import { Internship } from '../types/InternshipType';
import { Student } from '../types/StudentType';
import { Company } from '../types/CompanyType';

function InternshipDashboard() {
  const [internships, setInternships] = useState<Internship[]>([]);
  const [studentCount, setStudentCount] = useState<number>(0);
  const [companyCount, setCompanyCount] = useState<number>(0);
  const [approvedInternships, setApprovedInternships] = useState<number>(0);

  useEffect(() => {
    fetch('/api/internship')
      .then(response => response.json())
      .then((data: Internship[]) => {
        setInternships(data);
        setApprovedInternships(data.filter(internship => internship.status === "APPROVED").length);
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

  }, []);

  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <Link to="/facultysupervisor/AllInternships">
          <h2>Staj Yapan Öğrenciler</h2>
          <p>Toplam öğrenci: {studentCount}</p>
        </Link>
      </div>
      <div className="dashboard-card">
        <h2>Staj Başvuruları</h2>
        <p>{internships.length} adet başvuru bulundu.</p>
        {internships.map(internship => (
          <InternshipCard key={internship.id} internship={internship} />
        ))}
      </div>
      <div className="dashboard-card">
        <h2>Onaylanan Staj Başvurusu </h2>
        <p>{approvedInternships} adet staj başvurusu onaylandı.</p>
      </div>
    </div>
  );
}

export default InternshipDashboard;