import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import useSupervisorManagement from "../hooks/useSupervisorManagement";
import { tableHeaders } from "../constants";
import AddModalForm from "./AddModalForm";
import UpdateModalForm from "./UpdateModalForm";
import Pagination from "./Pagination";
import { CompanySupervisor } from "../types/CompanySupervisors";

interface ConfirmationBoxProps {
  onConfirm: (confirmed: boolean) => void;
}

const ConfirmationBox: React.FC<ConfirmationBoxProps> = ({ onConfirm }) => {
  const [isVisible, setIsVisible] = useState(true);

  const handleConfirm = (confirmed: boolean) => {
    setIsVisible(false);
    onConfirm(confirmed);
  };

  return (
    <div className="confirmation-box" style={{ display: isVisible ? "block" : "none" }}>
      <p>Silmek istediğinize emin misiniz?</p>
      <div className="confirmation-buttons">
        <button className="yes" onClick={() => handleConfirm(true)}>Evet</button>
        <button className="no" onClick={() => handleConfirm(false)}>Hayır</button>
      </div>
    </div>
  );
};

const CompanySupervisorsPage: React.FC = () => {
  const navigate = useNavigate();
  const {
    isAddModalOpen,
    setIsAddModalOpen,
    addCompanySupervisor,
    isUpdateModalOpen,
    selectedCompanySupervisor,
    updateCompanySupervisor,
    deleteCompanySupervisor,
    setIsUpdateModalOpen,
    companySupervisors,
    pagination,
    setSelectedCompanySupervisor,
    pageChangeHandler,
    companies,
  } = useSupervisorManagement();

  const [showConfirmation, setShowConfirmation] = useState(false);
  const [deletingId, setDeletingId] = useState<number | null>(null);

  const handleDeleteConfirmation = (confirmed: boolean) => {
    if (confirmed && deletingId) {
      deleteCompanySupervisor(deletingId);
    }
    setDeletingId(null);
    setShowConfirmation(false);
  };

  const handleDeleteCompanySupervisor = (id: number) => {
    setDeletingId(id);
    setShowConfirmation(true);
  };

  const handleOpenAddModal = () => {
    setIsAddModalOpen(true);
    document.body.style.overflow = "hidden";
  };

  const handleOpenUpdateModal = (supervisor: CompanySupervisor) => {
    setSelectedCompanySupervisor(supervisor);
    setIsUpdateModalOpen(true);
    document.body.style.overflow = "hidden";
  };

  const handleCloseUpdateModal = () => {
    setIsUpdateModalOpen(false);
    document.body.style.overflow = "auto";
  };

  return (
    <div className="container">
      {isAddModalOpen && (
        <AddModalForm
          showModal={isAddModalOpen}
          onShowModal={setIsAddModalOpen}
          companies={companies}
          onAddCompanySupervisors={addCompanySupervisor}
        />
      )}
      {isUpdateModalOpen && selectedCompanySupervisor && (
        <UpdateModalForm
          companySupervisorDto={selectedCompanySupervisor}
          onUpdateCompanySupervisor={updateCompanySupervisor}
          onClose={handleCloseUpdateModal}
        />
      )}
      {showConfirmation && (
        <ConfirmationBox onConfirm={handleDeleteConfirmation} />
      )}
      <button className="add-button" onClick={handleOpenAddModal}>
        <span>+ Şirket Yetkilisi Ekle</span>
      </button>

      {companySupervisors?.length > 0 ? (
        <>
          <table className="company-supervisors-table">
            <thead>
              <tr>
                {tableHeaders.map((header) => (
                  <th key={header}>{header}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {companySupervisors?.map((supervisor) => (
                <tr key={supervisor.id}>
                  <td>{supervisor.name}</td>
                  <td>{supervisor.surname}</td>
                  <td className="status-container">
                    <div
                      className={`status-indicator ${
                        supervisor.user.activity ? "active" : "inactive"
                      }`}
                    ></div>
                    <span>{supervisor.user.activity ? "Aktif" : "Pasif"}</span>
                  </td>
                  <td>{supervisor.company.name}</td>
                  <td>{supervisor.company.description}</td>
                  <td>{supervisor.company.email}</td>
                  <td>{supervisor.company.phoneNumber}</td>

                  <td>{supervisor.company.scope}</td>
                  <td>
                    <div className="edit-buttons">
                      <button className="edit-button" onClick={() => handleOpenUpdateModal(supervisor)}>
                        Düzenle
                      </button>{" "}
                      <button className="detail-button" onClick={() => { navigate(`/admin/companySupervisors/${supervisor.id}`); }}>
                        Detay
                      </button>{" "}
                      <button className="delete-button" onClick={() => handleDeleteCompanySupervisor(supervisor.id)}>
                        Sil
                      </button>{" "}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <Pagination
            itemsPerPage={10}
            totalItems={pagination?.totalElements || 0}
            onPageChange={pageChangeHandler}
            currentPage={pagination?.number || 0}
          />
        </>
      ) : (
        <div className="not-found">
          <p>Kayıtlar bulunamadı!</p>
        </div>
      )}
    </div>
  );
};

export default CompanySupervisorsPage;
