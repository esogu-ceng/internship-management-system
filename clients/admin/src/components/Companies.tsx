/** @format */

import useSupervisorManagement from "../hooks/useSupervisorManagement";

import { companyTableHeaders } from "../constants";  ///

import Pagination from "./Pagination";

const Companies = () => {
  const {
    pagination,
    pageChangeHandlerCompanies,
    companiesForList,
  } = useSupervisorManagement();
  return (
    <div className="container">
    
      {companiesForList?.length > 0 ? (
        <>
          <table>
            <thead>
              <tr>
                {companyTableHeaders.map((header) => (
                  <th key={header}>{header}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {companiesForList?.map((company) => (
                <tr key={company.id}>
                  <td>{company.name}</td>
                  <td>{company.scope}</td>
                  <td>{company.description}</td>
                  <td>{company.email}</td>
                  <td>{company.phoneNumber}</td>
                  <td>{company.address}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <Pagination
            itemsPerPage={10}
            totalItems={pagination?.totalElements || 0}
            onPageChange={pageChangeHandlerCompanies}
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

export default Companies;
