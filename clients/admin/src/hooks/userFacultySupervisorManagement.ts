/** @format */

import { useEffect, useState } from "react";
import {
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

    const addFacultySupervisor = async (request: FacultySupervisorCreate) => {
        setLoading(true);
        setError(null);
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
            getAllFacultySupervisors(pagination.number - 1);
        } else if (pagination.number >= updatedTotalPages) {
            getAllFacultySupervisors(updatedTotalPages - 1);
        } else {
            getAllFacultySupervisors(pagination.number);
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
    }, []);

    return {
        loading,
        error,
        isAddModalOpen,
        isUpdateModalOpen,
        selectedFacultySupervisor,
        facultySupervisors,
        pagination,
        setIsAddModalOpen,
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
