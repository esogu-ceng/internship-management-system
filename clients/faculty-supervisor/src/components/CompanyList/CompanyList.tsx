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
        {companies.map(({ id, name, address, phoneNumber, faxNumber, email, scope, description, createDate, updateDate }) => (
  <tr key={id}>
    <td>{name}</td>
    <td>{address}</td>
    <td>{phoneNumber}</td>
    <td>{faxNumber}</td>
    <td>{email}</td>
    <td>{scope}</td>
    <td>{description}</td>
    <td>{createDate}</td>
    <td>{updateDate}</td>
    <td>
      <button className="btnupdel" onClick={() => onUpdate(id)}>
        <FontAwesomeIcon icon={faEdit} />
      </button>
      <button className="btnupdel" onClick={() => onDelete(id)}>
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
