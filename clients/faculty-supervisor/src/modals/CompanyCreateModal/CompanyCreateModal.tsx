import React, { useState } from 'react';
import './CompanyCreateModal.css';
import { Company } from '../../types/Company';

interface CompanyCreateModalProps {
  isOpen: boolean;
  onClose: () => void;
  onCreate: (newCompany: Company) => void;
}

const CompanyCreateModal: React.FC<CompanyCreateModalProps> = ({ isOpen, onClose, onCreate }) => {
  const [newCompany, setNewCompany] = useState<Company>({
    id: 0,
    name: '',
    address: '',
    phoneNumber: '',
    faxNumber: '',
    email: '',
    scope: '',
    description: '',
    createDate: '',
    updateDate: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setNewCompany(prevCompany => ({
      ...prevCompany,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onCreate(newCompany);
    onClose();
  };

  if (!isOpen) {
    return null;
  }

  return (
    <div className="modal-container">
      <div className="modal-container-al">
      <div className="modal-content">
        <div className='head'>Şirket Oluştur</div>
        <form onSubmit={handleSubmit}>
          {/* Input fields for company details */}
          <div className="form-group">
            <label>İsim:</label>
            <input 
            type="text" 
            name="name" 
           
            onChange={handleChange}
            className="modal-input" />
          </div>
          <div className="form-group">
            <label>Adres:</label>
            <input
              type="text"
              name="address"
             
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Telefon Numarası:</label>
            <input
              type="text"
              name="phoneNumber"
            
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Fax Numarası:</label>
            <input
              type="text"
              name="faxNumber"

              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Email:</label>
            <input
              type="email"
              name="email"
            
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Alan:</label>
            <input
              type="text"
              name="scope"
              
              onChange={handleChange}
              className="modal-input"
            />
          </div>
          <div className="form-group">
            <label>Açıklama:</label>
            <textarea
              name="description"
             
              onChange={handleChange}
              className="modal-input"
            ></textarea>
          </div>
          {/* Add other input fields for company details */}
          <div className="form-group button-group">
  <button type="submit" className="save-button">Kaydet</button>
  <button type="button" className="cancel-button" onClick={onClose}>
    İptal
  </button>
</div>
        </form>
        </div>
      </div>
    </div>
  );
};

export default CompanyCreateModal;
