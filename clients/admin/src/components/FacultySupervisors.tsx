/** @format */

import useFacultySupervisorManagement from "../hooks/userFacultySupervisorManagement";
import { facultySupervisortableHeaders } from "../constants";

import AddModalForm from "./AddFacultySupervisorModalForm";
import UpdateModalForm from "./UpdateFacultySupervisorModalForm";
import Pagination from "./Pagination";
import { FacultySupervisor } from "../types/FacultySuperviosr";

const FacultySupervisorsPage = () => {
  const {
    isAddModalOpen,
    setIsAddModalOpen,
    addFacultySupervisor,
    isUpdateModalOpen,
    selectedFacultySupervisor,
    updateFacultySupervisor,
    deleteFacultySupervisor,
    setIsUpdateModalOpen,
    facultySupervisors,
    faculty,
    pagination,
    setSelectedFacultySupervisor,
    pageChangeHandler,
  } = useFacultySupervisorManagement();


  const handleAddModalOpen = () =>{
    setIsAddModalOpen(true);
    document.body.style.overflow = "hidden";
  }

  const handleUpdateModalOpen = (supervisor : FacultySupervisor) => {
    setSelectedFacultySupervisor(supervisor);
    setIsUpdateModalOpen(true);
    document.body.style.overflow = "hidden";
  }

  const handleUpdateModalClose = () => {
    setIsUpdateModalOpen(false);
    document.body.style.overflow = "auto";
  }

  const handleDeleteFacultySupervisor = (id: number) => {
    if (!window.confirm("Silmek istediğinize emin misiniz?")) return;
    deleteFacultySupervisor(id);
  }

  return (
    <div className="container">
      {isAddModalOpen && (
        <AddModalForm
          showModal={isAddModalOpen}
          onShowModal={setIsAddModalOpen}
          faculties={faculty}
          onAddFacultySupervisors={addFacultySupervisor}
        />
      )}
      {isUpdateModalOpen && selectedFacultySupervisor && (
        <UpdateModalForm
          facultySupervisorDto={selectedFacultySupervisor}
          onUpdateFacultySupervisor={updateFacultySupervisor}
          onClose={handleUpdateModalClose}
        />
      )}
      <button className="add-button" onClick={handleAddModalOpen}>
        <span>+ Fakülte Sorumlusu Ekle</span>
      </button>
        <>
          <table>
            <thead>
              <tr>
                {facultySupervisortableHeaders.map((header) => (
                  <th key={header}>{header}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {facultySupervisors?.map((supervisor) => (
                <tr key={supervisor?.id}>
                  <td>{supervisor?.name}</td>
                  <td>{supervisor?.surname}</td>
                  <td>{supervisor?.phoneNumber}</td>
                  <td>{supervisor?.supervisorNo}</td>
                  <td className="status-container">
                    <div
                      className={`status-indicator ${
                        supervisor.user.activity ? "active" : "inactive"
                      }`}></div>
                    <span>{supervisor.user.activity ? "Aktif" : "Pasif"}</span>
                  </td>
                  <td>
                    <div className="update-buttons">
                      <button
                        onClick={() => 
                          handleUpdateModalOpen(supervisor)
                        }>
                        Düzenle
                      </button>{" "}
                      <button
                        onClick={() => handleDeleteFacultySupervisor(supervisor.id)}>
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
    </div>
  );
};
export default FacultySupervisorsPage;
