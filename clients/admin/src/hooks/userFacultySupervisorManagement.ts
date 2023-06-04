/** @format */

import { useEffect, useState } from "react";
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
    const [faculty, setFaculty] = useState<
        Faculty[]
    >([]);
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
        console.log("what is getAll data", data);
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
        console.log("what is data", data.content)
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
        setLoading(true);
        setError(null);
        console.log("what is request", request);
        const errorMessage = "Error adding Faculty supervisor.";
        try {
        const url = "/api/facultySupervisor/saveFacultysupervisor";
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

        getAllFacultySupervisors(pagination?.number || 0);
        } catch (error) {
        setError(errorMessage);
        } finally {
        setLoading(false);
        }
    };

    const updateFacultySupervisor = async (request: FacultySuperviserUpdate) => {
        setLoading(true);
        setError(null);
        console.log("what is updated requested data", request);
        const errorMessage = "Error updating Faculty supervisor.";
        try {
        const url = "/api/facultySupervisor";
        const response = await fetch(url, {
            method: "PUT",
            headers: {
            "Content-Type": "application/json",
            },
            body: JSON.stringify(request),
        });

        if (!response.ok) {
            throw new Error(errorMessage);
        }

        getAllFacultySupervisors(pagination?.number || 0);
        } catch (error) {
        setError(errorMessage);
        } finally {
        setLoading(false);
        }
    };

    const deleteFacultySupervisor = async (id: number) => {
        setLoading(true);
        setError(null);
        const errorMessage = "Error deleting Faculty supervisor.";
        try {
        const url = `/api/facultySupervisor/${id}`;
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

        if (facultySupervisors.length === 1 && pagination.number > 0) {
            let paginationNumbers = pagination.number - 1;
            if(paginationNumbers < 0){
                paginationNumbers = 0;
            }
            getAllFacultySupervisors(paginationNumbers);
        } else if (pagination.number >= updatedTotalPages) {
            let paginationNumbers = updatedTotalPages - 1;
            if(paginationNumbers < 0){
                paginationNumbers = 0;
            }
            getAllFacultySupervisors(paginationNumbers);
        } else {
            let paginationNumbers = pagination.number;
            if(paginationNumbers < 0){
                paginationNumbers = 0;
            }
            getAllFacultySupervisors(paginationNumbers);
        }
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
