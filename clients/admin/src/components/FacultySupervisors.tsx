/** @format */

import useFacultySupervisorManagement from "../hooks/userFacultySupervisorManagement";
import { facultySupervisortableHeaders } from "../constants";

import AddModalForm from "./AddFacultySupervisorModalForm";
import UpdateModalForm from "./UpdateFacultySupervisorModalForm";
import Pagination from "./Pagination";

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
          onClose={() => setIsUpdateModalOpen(false)}
        />
      )}
      <button className="add-button" onClick={() => setIsAddModalOpen(true)}>
        <span>+ Fakülte Sorumlusu Ekle</span>
      </button>
      {facultySupervisors?.length > 0 ? (
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
                    <div className="edit-buttons">
                      <button
                        onClick={() => {
                          setSelectedFacultySupervisor(supervisor);
                          setIsUpdateModalOpen(true);
                        }}>
                        Edit
                      </button>{" "}
                      <button
                        onClick={() => deleteFacultySupervisor(supervisor.id)}>
                        Delete
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
export default FacultySupervisorsPage;
