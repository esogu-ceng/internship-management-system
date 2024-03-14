/** @format */

import { useNavigate } from "react-router-dom";

import useSupervisorManagement from "../hooks/useSupervisorManagement";

import { tableHeaders } from "../constants";

import AddModalForm from "./AddModalForm";
import UpdateModalForm from "./UpdateModalForm";
import Pagination from "./Pagination";
import { CompanySupervisor } from "../types/CompanySupervisors";

const CompanySupervisorsPage = () => {
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

  const handleOpenAddModal = () => {
    setIsAddModalOpen(true);
    document.body.style.overflow = "hidden";
  }

  const handleOpenUpdateModal = (supervisor: CompanySupervisor) => {
      setSelectedCompanySupervisor(supervisor);
      setIsUpdateModalOpen(true);
       document.body.style.overflow = "hidden";
  }

  const handleCloseUpdateModal = () => {
    setIsUpdateModalOpen(false);
    document.body.style.overflow = "auto";
  }

  const handleDeleteCompanySupervisor = (id: number) => {
    if (!window.confirm("Silmek istediğinize emin misiniz?")) return;
    deleteCompanySupervisor(id);
  }


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
      <button className="add-button" onClick={handleOpenAddModal}>
        <span>+ Şirket Yetkilisi Ekle</span>
      </button>

      {companySupervisors?.length > 0 ? (
        <>
          <table>
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
                      }`}></div>
                    <span>{supervisor.user.activity ? "Aktif" : "Pasif"}</span>
                  </td>
                  <td>{supervisor.company.name}</td>
                  <td>{supervisor.company.description}</td>
                  <td>{supervisor.company.email}</td>
                  <td>{supervisor.company.phoneNumber}</td>

                  <td>{supervisor.company.scope}</td>
                  <td>
                    <div className="edit-buttons">
                      <button
                        onClick={() => handleOpenUpdateModal(supervisor)}>
                        Düzenle
                      </button>{" "}
                      <button
                        onClick={() => {
                          navigate(
                            `/admin/companySupervisors/${supervisor.id}`
                          );
                        }}>
                        Detay
                      </button>{" "}
                      <button
                        onClick={() => handleDeleteCompanySupervisor(supervisor.id)}>
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
