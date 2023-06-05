import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import {
  StudentUpdate,
  Student,
  StudentCreate,
  StudentPagedResponse,
  Faculty,
  FacultyPagedResponse,
  Pageable,
} from "../types/StudentType";

const useStudentManagement = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [isAddModalOpen, setIsAddModalOpen] = useState<boolean>(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState<boolean>(false);
  const [selectedStudent, setSelectedStudent] =
    useState<Student>();
  const [faculties, setFaculties] = useState<Faculty[]>([]);
  const [students, setStudents] = useState<
    Student[]
  >([]);
  const [pagination, setPagination] = useState<Pageable>();

  const getAllStudents = async (
    pageNo: number = 0,
    limit: number = 10,
    sortBy: string = "name"
  ) => {
    setLoading(true);

    const errorMessage = "Error retrieving students.";

    try {
      const url = `/api/student/getAll?pageNo=${pageNo}&limit=${limit}&sortBy=${sortBy}`;
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      const data: StudentPagedResponse = await response.json();

      setStudents(data.content);
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

  const addStudent = async (request: StudentCreate) => {
    setLoading(true);
    setError(null);
    const errorMessage = "Öğrenci eklenirken hata oluştu!";
    const successMessage = "Öğrenci başarıyla eklendi!";
    try {
      const url = "/api/student";
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

      getAllStudents(pagination?.number || 0);
      toast.success(successMessage);
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const updateStudent = async (request: StudentUpdate) => {
    setLoading(true);
    setError(null);
    const errorMessage = "Öğrenci güncellenirken hata oluştu!";
    const successMessage = "Öğrenci başarıyla güncellendi!";
    try {
      const url = "/api/student";
      const response = await fetch(url, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      });

      if (!response.ok) {
        toast.error(errorMessage)
        throw new Error(errorMessage);
      }

      getAllStudents(pagination?.number || 0);
      toast.success(successMessage)
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const deleteStudent = async (id: number) => {
    setLoading(true);
    setError(null);
    const errorMessage = "Öğrenci silinirken hata oluştu!";
    const successMessage = "Öğrenci başarıyla silindi!";
    try {
      const url = `/api/student/${id}`;
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

      if (!pagination) return;
      const updatedTotalItems = pagination.totalElements - 1;
      const updatedTotalPages = Math.ceil(updatedTotalItems / pagination.size);

      if (students.length === 1 && pagination.number > 0) {
        getAllStudents(pagination.number - 1);
      } else if (pagination.number >= updatedTotalPages && pagination.number !== 0) {
        getAllStudents(updatedTotalPages - 1);
      } else {
        getAllStudents(pagination.number);
      }
      toast.success(successMessage)
    } catch (error) {
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };
  
  const getFaculties = async (
    pageNo: number = 0,
    limit: number = 10,
    sortBy: string = "name"
  ) => {
    setLoading(true);

    const errorMessage = "Error retrieving faculties.";

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

      console.log("data : ", data);

      setFaculties(data.content);
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

  const handleCheckboxChange = async (studentId : number) => {
    try {
      const updatedStudents = students.map((student) => {
        if (student.id === studentId) {
          return {
            ...student,
            user: {
              ...student.user,
              activity: !student.user.activity,
            },
          };
        }
        return student;
      });
  
      setStudents(updatedStudents);
      const userId = updatedStudents.find((student) => student.id === studentId)?.user.id;
      const activity = updatedStudents.find((student) => student.id === studentId)?.user.activity;
      const url = `/api/user/activity/${userId}?status=${activity}`;
      const response = await fetch(url, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      if (!response.ok) {
        toast.error("Öğrenci aktiflik durumu değiştirilirken hata oluştu!")
        throw new Error('Failed to update student activity.');
      }
      if (activity){
        toast.success('Öğrenci aktif hale getirildi!');
      }
      else{
        toast.success('Öğrenci pasif hale getirildi!');
      }
      
    } catch (error) {
      console.error('Error updating student activity:', error);
    }
  };

  const pageChangeHandler = (pageNumber: number) => {
    if (!pagination) return;

    setPagination({ ...pagination, number: pageNumber });
    getAllStudents(pageNumber);
  };

  useEffect(() => {
    getAllStudents();
    getFaculties();
  }, []);

  return {
    loading,
    error,
    isAddModalOpen,
    isUpdateModalOpen,
    selectedStudent,
    students,
    pagination,
    faculties,
    getFaculties,
    setIsAddModalOpen,
    getAllStudents,
    addStudent,
    updateStudent,
    deleteStudent,
    pageChangeHandler,
    setIsUpdateModalOpen,
    setSelectedStudent,
    handleCheckboxChange
  };
};

export default useStudentManagement;