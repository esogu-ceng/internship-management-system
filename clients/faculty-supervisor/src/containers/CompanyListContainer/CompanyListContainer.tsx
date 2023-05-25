import React, { useEffect, useState } from 'react';
import axios from 'axios';
import CompanyList from '../../components/CompanyList/CompanyList';
import CompanyUpdateModal from '../../modals/CompanyUpdateModal/CompanyUpdateModal';
import './CompanyListContainer.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft, faChevronRight, faEdit, faTrash } from '@fortawesome/free-solid-svg-icons';
import CompanyCreateModal from '../../modals/CompanyCreateModal/CompanyCreateModal';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import ErrorModal from '../../modals/ErrorModal/ErrorModal';
import { Company } from '../../types/Company';

interface PageableResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
}

const CompanyListContainer: React.FC = () => {
  const [companies, setCompanies] = useState<Company[]>([]);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(3);
  const [sortBy, setSortBy] = useState<string>('name');
  const [totalPages, setTotalPages] = useState<number>(0);
  const [selectedCompany, setSelectedCompany] = useState<Company | null>(null);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [selectedCompanyId, setSelectedCompanyId] = useState<number | null>(null);
  const [isCreateModalOpen, setCreateModalOpen] = useState<boolean>(false);
  const [showErrorModal, setShowErrorModal] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>('');

  const handleCreateModalOpen = () => {
    setCreateModalOpen(true);
  };

  const handleCreateModalClose = () => {
    setCreateModalOpen(false);
  };


  const fetchCompanies = async (page: number, size: number, sort: string) => {
    try {
      const response = await axios.get('/api/company/getAll', {
        params: {
          pageNo: page,
          limit: size,
          sortBy: sort,
        },
        
      });

      const { content, totalPages } = response.data as PageableResponse<Company>;
      setCompanies(content);
      setTotalPages(totalPages);
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (companyId: number) => {
    try {
      await axios.delete(`/api/company/${companyId}`, {
        
      });

      // Refresh the company list after successful deletion
      fetchCompanies(currentPage, pageSize, sortBy);
    } catch (error) {
      console.error(error);
    }
  };

  const handleUpdateCompany = (companyId: number) => {
    const selected = companies.find(company => company.id === companyId);
    if (selected) {
      setSelectedCompany(selected);
      setIsModalOpen(true);
      setSelectedCompanyId(companyId)
    }
  };
  const handleCreateCompany = async (newCompany: Company) => {
    try {
      await axios.post(`/api/company/`, newCompany, {
      
      });

      // Refresh the company list after creating a new company
      fetchCompanies(currentPage, pageSize, sortBy);
    } catch (error) {
      console.error(error);
      setErrorMessage('Failed to create the company.'); // Set the error message
      setShowErrorModal(true);
    }
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setSelectedCompany(null);
  };
  const handleCloseErrorModal = () => {
    setShowErrorModal(false); // Hide the error modal
    setErrorMessage(''); // Clear the error message
  };
  const handleUpdate = async (updatedCompany: Company) => {
    try {
      await axios.put(`/api/company/`, updatedCompany, {
      
      });

      // Refresh the company list after update
      fetchCompanies(currentPage, pageSize, sortBy);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchCompanies(currentPage, pageSize, sortBy);
  }, [currentPage, pageSize, sortBy]);

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(prevPage => prevPage - 1);
    } else {
      // Handle the case when the user is already on the first page
      // You can show a message or disable the "Previous Page" button
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(prevPage => prevPage + 1);
    } else {
      // Handle the case when the user is already on the last page
      // You can show a message or disable the "Next Page" button
    }
  };

  return (
    <div>
       
      <div className="pagination-settings">
        <label>
          Page Size:
          <input
            type="number"
            value={pageSize}
            onChange={e => setPageSize(parseInt(e.target.value))}
          />
        </label>
        <label>
          Sort By:
          <select value={sortBy} onChange={e => setSortBy(e.target.value)}>
            <option value="name">İsim</option>
            <option value="address">Adres</option>
            <option value="phoneNumber">Telefon Numarası</option>
            <option value="faxNumber">Fax Numarası</option>
            <option value="email">Email</option>
            <option value="scope">Alan</option>
            <option value="description">Açıklama</option>
            <option value="createDate">Oluşturma Tarihi</option>
            <option value="updateDate">Güncellenme Tarihi</option>
            {/* Add other sortable fields here */}
          </select>
        </label>
      </div>
      <button className="create-button-container right-align" onClick={handleCreateModalOpen}><FontAwesomeIcon className="plusicon"icon={faPlus} />Create Company</button>
      <div className="pagination-controls">
        <button className="previous" onClick={handlePreviousPage} disabled={currentPage === 0}>
          <FontAwesomeIcon icon={faChevronLeft} />
        </button>
        <button className="next" onClick={handleNextPage} disabled={currentPage === totalPages - 1}>
          <FontAwesomeIcon icon={faChevronRight} />
        </button>
      </div>
      
      <CompanyList
        companies={companies}
        onUpdate={handleUpdateCompany}
        onDelete={handleDelete}
      />
       {showErrorModal && <ErrorModal message={errorMessage} onClose={handleCloseErrorModal} />} {/* Show the error modal if showErrorModal is true */}
      {selectedCompany && (
        <CompanyUpdateModal
          isOpen={isModalOpen}
          onClose={handleModalClose}
          onUpdate={handleUpdate}
          company={selectedCompany}
        />
      )}
       <CompanyCreateModal isOpen={isCreateModalOpen} onClose={handleCreateModalClose} onCreate={handleCreateCompany} />
    </div>
  );
};

export default CompanyListContainer;
