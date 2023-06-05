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
    </div>
  );
};

export default Companies;
