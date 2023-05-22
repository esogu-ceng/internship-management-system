import React from 'react';
import './CompanyList.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Company } from '../../types/Company';


interface CompanyListProps {
  companies: Company[];
  onUpdate: (companyId: number) => void;
  onDelete: (companyId: number) => void;
}

const CompanyList: React.FC<CompanyListProps> = ({ companies, onUpdate, onDelete }) => {
  return (
    <div className="company-list">
      <table>
        <thead>
          <tr>
            <th>İsim</th>
            <th>Adres</th>
            <th>Telefon Numarası</th>
            <th>Fax Numarası</th>
            <th>Email</th>
            <th>Alan</th>
            <th>Açıklama</th>
            <th>Oluşturma Tarihi</th>
            <th>Güncellenme Tarihi</th>
            <th>Eylemler</th>
          </tr>
        </thead>
        <tbody>
          {companies.map(company => (
            <tr key={company.id}>
              <td>{company.name}</td>
              <td>{company.address}</td>
              <td>{company.phoneNumber}</td>
              <td>{company.faxNumber}</td>
              <td>{company.email}</td>
              <td>{company.scope}</td>
              <td>{company.description}</td>
              <td>{company.createDate}</td>
              <td>{company.updateDate}</td>
              <td>
                <button className="btnupdel" onClick={() => onUpdate(company.id)}>
                  <FontAwesomeIcon icon={faEdit} />
                </button>
                <button className="btnupdel" onClick={() => onDelete(company.id)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CompanyList;
