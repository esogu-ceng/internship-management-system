/** @format */

import useSupervisorManagement from "../hooks/useSupervisorManagement";

import { tableHeaders } from "../constants";

import AddModalForm from "./AddModalForm";
import UpdateModalForm from "./UpdateModalForm";
import Pagination from "./Pagination";

const CompanySupervisorsPage = () => {
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
          onClose={() => setIsUpdateModalOpen(false)}
        />
      )}
      <button className="add-button" onClick={() => setIsAddModalOpen(true)}>
        <span>+ Şirket Yetkilisi Ekle</span>
      </button>

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
              <td>{supervisor.company.name}</td>
              <td>{supervisor.company.description}</td>
              <td>{supervisor.company.email}</td>
              <td>{supervisor.company.faxNumber}</td>
              <td>{supervisor.company.phoneNumber}</td>
              <td>{supervisor.company.scope}</td>
              <td>
                <div className="edit-buttons">
                  <button
                    onClick={() => {
                      setSelectedCompanySupervisor(supervisor);
                      setIsUpdateModalOpen(true);
                    }}>
                    Edit
                  </button>{" "}
                  <button
                    onClick={() => deleteCompanySupervisor(supervisor.id)}>
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
    </div>
  );
};

export default CompanySupervisorsPage;
