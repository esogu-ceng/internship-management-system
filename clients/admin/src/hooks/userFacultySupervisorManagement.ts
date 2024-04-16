/** @format */

import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  Faculty,
  FacultyPagedResponse,
  FacultySuperviserUpdate,
  FacultySupervisor,
  FacultySupervisorCreate,
  FacultySupervisorPagedResponse,
  Pageable,
} from "../types/FacultySuperviosr";

const useFacultySupervisorManagement = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [isAddModalOpen, setIsAddModalOpen] = useState<boolean>(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState<boolean>(false);
  const [selectedFacultySupervisor, setSelectedFacultySupervisor] =
    useState<FacultySupervisor>();
  const [facultySupervisors, setFacultySupervisors] = useState<
    FacultySupervisor[]
  >([]);
  const [faculty, setFaculty] = useState<Faculty[]>([]);
  const [pagination, setPagination] = useState<Pageable>();

  const getAllFacultySupervisors = async (
    pageNo: number = 0,
    limit: number = 10,
    sortBy: string = "name"
  ) => {
    setLoading(true);
    const errorMessage = "Error retrieving Faculty supervisors.";
    try {
      const url = `/api/facultySupervisor/supervisors?pageNo=${pageNo}&limit=${limit}&sortBy=${sortBy}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      const data: FacultySupervisorPagedResponse = await response.json();
      setFacultySupervisors(data.content);
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
  const getAllFaculties = async (
    pageNo: number = 0,
    limit: number = 10,
    sortBy: string = "name"
  ) => {
    setLoading(true);
    const errorMessage = "Error retrieving Faculty supervisors.";
    try {
      const url = `/api/faculty/getAll?pageNo=${pageNo}&limit=${limit}&sortBy=${sortBy}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }
      const data: FacultyPagedResponse = await response.json();
      setFaculty(data.content);
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

  const addFacultySupervisor = async (request: FacultySupervisorCreate) => {
    const successMessage = "Fakülte sorumlusu başarıyla eklendi!";
    const errorMessage = "Fakülte sorumlusu eklenirken hata oluştu!";
    try {
      setLoading(true);
      setError(null);
      const url = "/api/facultySupervisor/saveFacultysupervisor";
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      });
      if (!response.ok) {
        toast.error(errorMessage)
        throw new Error(errorMessage);
      }
      getAllFacultySupervisors(pagination?.number || 0);
      toast.success(successMessage); // Display success message
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const updateFacultySupervisor = async (request: FacultySuperviserUpdate) => {
    const successMessage = "Fakülte sorumlusu başarıyla güncellendi!";
    const errorMessage = "Fakülte sorumlusu güncellenirken hata oluştu!";
    try {
      setLoading(true);
      setError(null);
      const [response, activityResponse] = await Promise.all([
        fetch("/api/facultySupervisor", {
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
        toast.error(errorMessage)
        throw new Error("Error updating faculty supervisor.");
      }
      getAllFacultySupervisors(pagination?.number || 0);
      toast.success(successMessage); // Display success message
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const deleteFacultySupervisor = async (id: number) => {
    setLoading(true);
    setError(null);
    const successMessage = "Fakülte sorumlusu başarıyla silindi!";
    const errorMessage = "Fakülte sorumlusu silinirken hata oluştu!";
    
    try {
      const url = `/api/facultySupervisor/${id}`;
      const response = await fetch(url, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        toast.error(errorMessage)
        throw new Error(errorMessage);
      }

      const data = await response.json();
      if(!data){
        toast.error(errorMessage);
        return;
      }


      if (!pagination) return;
      const updatedTotalItems = pagination.totalElements - 1;
      const updatedTotalPages = Math.ceil(updatedTotalItems / pagination.size);

      if (facultySupervisors.length === 1 && pagination.number > 0) {        
        getAllFacultySupervisors(pagination.number - 1);
      }
      else if (pagination.number >= updatedTotalPages) {
        getAllFacultySupervisors(updatedTotalPages - 1);
      }
      else {
        getAllFacultySupervisors(pagination.number);
      }
      toast.success(successMessage); // Display success message
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const pageChangeHandler = (pageNumber: number) => {
    if (!pagination) return;

    setPagination({ ...pagination, number: pageNumber });
    getAllFacultySupervisors(pageNumber);
  };

  useEffect(() => {
    getAllFacultySupervisors();
    getAllFaculties();
  }, []);

  return {
    loading,
    error,
    isAddModalOpen,
    isUpdateModalOpen,
    selectedFacultySupervisor,
    facultySupervisors,
    faculty,
    pagination,
    setIsAddModalOpen,
    getAllFaculties,
    getAllFacultySupervisors,
    addFacultySupervisor,
    updateFacultySupervisor,
    deleteFacultySupervisor,
    pageChangeHandler,
    setIsUpdateModalOpen,
    setSelectedFacultySupervisor,
  };
};

export default useFacultySupervisorManagement;
