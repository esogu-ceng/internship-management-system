/** @format */

import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import {
  Company,
  CompanyOption,
  CompanyPagedResponse,
  CompanyPagedResponseForList,  ///
  CompanySuperviserUpdate,
  CompanySupervisor,
  CompanySupervisorCreate,
  CompanySupervisorPagedResponse,
  Pageable,
} from "../types/CompanySupervisors";

const useSupervisorManagement = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [isAddModalOpen, setIsAddModalOpen] = useState<boolean>(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState<boolean>(false);
  const [selectedCompanySupervisor, setSelectedCompanySupervisor] =
    useState<CompanySupervisor>();
  const [companies, setCompanies] = useState<CompanyOption[]>([]);
  const [companiesForList, setCompaniesForList] = useState<Company[]>([]);  //


  const [companySupervisors, setCompanySupervisors] = useState<
    CompanySupervisor[]
  >([]);
  const [pagination, setPagination] = useState<Pageable>();

  const getAllCompanySupervisors = async (
    pageNo: number = 0,
    limit: number = 10,
    sortBy: string = "name"
  ) => {
    setLoading(true);

    const errorMessage = "Error retrieving company supervisors.";

    try {
      const url = `/api/company-supervisor?pageNo=${pageNo}&limit=${limit}&sortBy=${sortBy}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      const data: CompanySupervisorPagedResponse = await response.json();

      setCompanySupervisors(data.content);
      setPagination({
        totalElements: data.totalElements,
        totalPages: data.totalPages,
        number: data.number,
        size: data.size,
      });
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const getCompanySupervisor = async (id: number) => {
    setLoading(true);

    const errorMessage = "Error retrieving company supervisor.";

    try {
      const url = `/api/company-supervisor/${id}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      const data: CompanySupervisor = await response.json();

      setSelectedCompanySupervisor(data);
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const addCompanySupervisor = async (request: CompanySupervisorCreate) => {
    setLoading(true);
    setError(null);
    const errorMessage = "Error adding company supervisor.";
    const successMessage = "Company supervisor added successfully.";

    try {
      const url = "/api/company-supervisor";
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      getAllCompanySupervisors(pagination?.number || 0);
      toast.success(successMessage); // Display success message
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const updateCompanySupervisor = async (request: CompanySuperviserUpdate) => {
    try {
      setLoading(true);
      setError(null);

      const [response, activityResponse] = await Promise.all([
        fetch("/api/company-supervisor", {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(request),
        }),
        fetch(
          `/api/user/activity/${request.user.id}?status=${request.user.activity}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
          }
        ),
      ]);

      if (!response.ok || !activityResponse.ok) {
        throw new Error("Error updating company supervisor.");
      }

      getAllCompanySupervisors(pagination?.number || 0);
      toast.success("Company supervisor updated successfully.");
    } catch (error) {
      setError("Error updating company supervisor.");
    } finally {
      setLoading(false);
    }
  };

  const deleteCompanySupervisor = async (id: number) => {
    setLoading(true);
    setError(null);
    const errorMessage = "Error deleting company supervisor.";
    const successMessage = "Company supervisor deleted successfully.";

    try {
      const url = `/api/company-supervisor/${id}`;
      const response = await fetch(url, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      if (!pagination) return;
      const updatedTotalItems = pagination.totalElements - 1;
      const updatedTotalPages = Math.ceil(updatedTotalItems / pagination.size);

      if (companySupervisors.length === 1 && pagination.number > 0) {
        getAllCompanySupervisors(pagination.number - 1);
      } else if (pagination.number >= updatedTotalPages) {
        getAllCompanySupervisors(updatedTotalPages - 1);
      } else {
        getAllCompanySupervisors(pagination.number);
      }

      toast.success(successMessage); // Display success message
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const getCompanies = async (
    pageNo: number = 0,
    limit: number = 10,
    sortBy: string = "name"
  ) => {
    setLoading(true);

    const errorMessage = "Error retrieving companies.";

    try {
      const url = `/api/company/getAll?pageNo=${pageNo}&limit=${limit}&sortBy=${sortBy}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      const data: CompanyPagedResponse = await response.json();

      setCompanies(data.content);
      // setPagination({
      //   totalElements: data.totalElements,
      //   totalPages: data.totalPages,
      //   number: data.number,
      //   size: data.size,
      // });
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const getCompaniesForCompaniesPage = async (    //////
    pageNo: number = 0,
    limit: number = 10,
    sortBy: string = "name"
  ) => {
    setLoading(true);

    const errorMessage = "Error retrieving companies.";

    try {
      const url = `/api/company/getAll?pageNo=${pageNo}&limit=${limit}&sortBy=${sortBy}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      const data: CompanyPagedResponseForList = await response.json();
      console.log(data)
      setCompaniesForList(data.content);
      setPagination({
        totalElements: data.totalElements,
        totalPages: data.totalPages,
        number: data.number,
        size: data.size,
      });
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const pageChangeHandler = (pageNumber: number) => {
    if (!pagination) return;

    setPagination({ ...pagination, number: pageNumber });
    getAllCompanySupervisors(pageNumber);
  };

  const pageChangeHandlerCompanies = (pageNumber: number) => {
    if (!pagination) return;

    setPagination({ ...pagination, number: pageNumber });
    getCompaniesForCompaniesPage(pageNumber);
  };

  useEffect(() => {
    getAllCompanySupervisors();
    getCompanies();
    getCompaniesForCompaniesPage();
  }, []);

  return {
    loading,
    error,
    isAddModalOpen,
    isUpdateModalOpen,
    selectedCompanySupervisor,
    companySupervisors,
    pagination,
    companies,
    companiesForList,
    getCompanySupervisor,
    getCompanies,
    getCompaniesForCompaniesPage,
    setIsAddModalOpen,
    getAllCompanySupervisors,
    addCompanySupervisor,
    updateCompanySupervisor,
    deleteCompanySupervisor,
    pageChangeHandler,
    pageChangeHandlerCompanies,
    setIsUpdateModalOpen,
    setSelectedCompanySupervisor,
  };
};

export default useSupervisorManagement;
