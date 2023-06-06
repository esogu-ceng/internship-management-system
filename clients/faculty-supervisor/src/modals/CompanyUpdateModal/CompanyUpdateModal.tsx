import React, { useState } from 'react';
import './CompanyUpdateModal.css';
import { Company } from '../../types/Company';

interface CompanyUpdateModalProps {
  isOpen: boolean;
  onClose: () => void;
  onUpdate: (updatedCompany: Company) => void;
  company: Company;
 
}


const CompanyUpdateModal: React.FC<CompanyUpdateModalProps> = ({
  isOpen,
  onClose,
  onUpdate,
  company,
}) => {
  const [updatedCompany, setUpdatedCompany] = useState<Company>({
    ...company,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setUpdatedCompany(prevCompany => ({
      ...prevCompany,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onUpdate(updatedCompany);
    onClose();
  };

  if (!isOpen) {
    return null;
  }

  return (
    <div className="modal-container">
       <div className="modal-container-al">
      <div className="modal-contenta">
        <div className='head'>Şirketi Güncelle</div>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>İsim:</label>
            <input
              type="text"
              name="name"
              value={updatedCompany.name}
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Adres:</label>
            <input
              type="text"
              name="address"
              value={updatedCompany.address}
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Telefon Numarası:</label>
            <input
              type="text"
              name="phoneNumber"
              value={updatedCompany.phoneNumber}
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Fax Numarası:</label>
            <input
              type="text"
              name="faxNumber"
              value={updatedCompany.faxNumber}
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Email:</label>
            <input
              type="email"
              name="email"
              value={updatedCompany.email}
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Alan:</label>
            <input
              type="text"
              name="scope"
              value={updatedCompany.scope}
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Açıklama:</label>
            <textarea
              name="description"
              value={updatedCompany.description}
              onChange={handleChange}
              className="modal-input"
            ></textarea>
          </div>
          <div className="form-group  button-group">
            <button className="save-button" type="submit">Güncelle</button>
            <button className = "cancel-button"type="button" onClick={onClose}>
              İptal
            </button>
          </div>
        </form>
      </div>
      </div>
    </div>
  );
};

export default CompanyUpdateModal;
