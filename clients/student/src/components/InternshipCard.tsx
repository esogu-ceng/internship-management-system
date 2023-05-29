import React from 'react';
import { Internship } from '../types/InternshipType';

interface InternshipCardProps {
  internship: Internship; 
}

const InternshipCard: React.FC<InternshipCardProps> = ({ internship }) => {
  return (
    <div className="card">
      <div className="card-body">
        <h5 className="card-title">{`${internship.student.name} ${internship.student.surname}`}</h5>
        <h6 className="card-subtitle mb-2 text-muted">{`${internship.startDate.toString()} - ${internship.endDate.toString()}`}</h6>
        <p className="card-text">{`Company ID: ${internship.companyId} - ${internship.days} days`}</p>
        <p className="card-text">{`Supervisor ID: ${internship.facultySupervisorId}`}</p>
      </div>
    </div>
  );
}

export default InternshipCard;