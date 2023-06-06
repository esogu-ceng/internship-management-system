import React, { useEffect, useState } from 'react';
import { Internship } from '../types/InternshipType';

interface InternshipCardProps {
  internship: Internship; 
}

const InternshipCard: React.FC<InternshipCardProps> = ({ internship }) => {
  return (
    <div className="card">
      <div className="card-body">
        <h5 className="card-title">{`${internship.student.name} ${internship.student.surname}`}</h5>
        <h6 className="card-subtitle mb-2 text-muted">{internship.createDate.toString()}</h6>
        <p className="card-text">{`${internship.company.name} - ${internship.days} days`}</p>
        <p className="card-text">{`Supervisor: ${internship.facultySupervisor.id} ${internship.facultySupervisor.id}`}</p>
      </div>
    </div>
  );
}

export default InternshipCard; 