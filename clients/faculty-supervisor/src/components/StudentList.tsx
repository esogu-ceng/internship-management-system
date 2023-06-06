import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft, faChevronRight, faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Student } from '../types/StudentListType';
import { StudentAdd } from '../modals/StudentAddModal/StudentAddModal';
import { StudentUpdate } from '../modals/StudentUpdateModal/StudentUpdateModal';

type ModalProps = {
  _facultySupervisorId: number | null;
}

const StudentList: React.FC <ModalProps> =({_facultySupervisorId}) => {
    const [students, setStudents] = useState<Student[]>([]);
    const [studentId, setStudentId] = useState<number | null>(null);
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [pageSize, setPageSize] = useState<number>(3);
    const [sortBy, setSortBy] = useState<string>('name');
    const [totalPages, setTotalPages] = useState<number>(0);
    const [open, setOpen] = useState(false);
    const [showUpdateModal, setUpdateModal] = useState<boolean>(false);
    const [selectedStudentId, setSelectedStudentId] = useState<number | null>(null);
    const [showAddModal, setAddModal] = useState<boolean>(false);
    const closeUpdateModal = () => {
      setUpdateModal(false);
    };
    const closeAddModal = () => {
      setAddModal(false);
    };
    const fetchFacultyById = async (facultyId:number) => {
      try {
        const response = await axios.get(`/api/faculty/${facultyId}`);
        const { data } = response;
        return data;
      } catch (error) {
        console.error(error);
        throw error;
      }
    };
    const fetchStudentsByFacultySupervisorId = async (
        pageNo = 0,
        limit = 10,
        sortBy = 'id'
      ) => {
        try {
          const response = await axios.get(`/api/student/supervisor/${_facultySupervisorId}`, {
            params: {
              pageNo,
              limit,
              sortBy,
            },
          });
          const { data } = response;
          const { content, totalPages } = data;
          const updatedStudents = await Promise.all<Student>(content.map(async (student: Student) => {
            const facultyData = await fetchFacultyById(student.faculty.id);
            return {
              ...student,
              faculty: {
                id: facultyData.id,
                name: facultyData.name,
              },
            };
          }));
          setStudents(content);
          setTotalPages(totalPages);
        } catch (error) {
          console.error(error);
          throw error;
        }
      };
    
      useEffect(() => {
        fetchStudentsByFacultySupervisorId(currentPage, pageSize, sortBy);
      }, [currentPage, pageSize, sortBy]);

      const handleAdd = async () => {
        try {
          setSelectedStudentId(studentId);
          setAddModal(true);
        } catch (error) {
          console.error(error);
        }
      };
      const handleDelete = async (_studentId: number) => {
        const confirmed = window.confirm('Öğrenciyi silmek istediğinize emin misiniz?');
        if (confirmed) {} 
        else {
          console.log("Silme işlemi iptal edildi.");
        }
      };
      
      const handleUpdate = async (studentId:number) => {
        try {
          setSelectedStudentId(studentId);
          setUpdateModal(true);
          fetchStudentsByFacultySupervisorId(currentPage, pageSize, sortBy);
        } catch (error) {
          console.error(error);
        }
      };
      const handlePreviousPage = () => {
        if (currentPage > 0) {
          setCurrentPage(prevPage => prevPage - 1);
        } else {

        }
      };
      const handleNextPage = () => {
        if (currentPage < totalPages - 1) {
          setCurrentPage(prevPage => prevPage + 1);
        } else {
       
        }
      };    
      return (
          <div>
          <br></br>
          <button className="add-student" onClick={handleAdd} style={{ backgroundColor: 'blue', color: 'white', borderRadius: '20px', padding: '10px 20px', boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.2)', fontWeight: 'bold', marginLeft: '1150px' }}>
          <span style={{ marginRight: '5px' }}>+</span>Öğrenci Ekle
          </button>
          <br></br>
          <br></br> 
          <div className="student-list">
          <table>
            <thead>
              <tr>
                <th>İsim</th>
                <th>Soyadı</th>
                <th>TCKN</th>
                <th>Öğrenci Numarası</th>
                <th>Ortalama</th>
                <th>Telefon Numarası</th>
                <th>Doğum yeri</th>
                <th>Doğum Tarihi</th>
                <th>Fakülte</th>
                <th>Adres</th>
                <th>İşlemler</th>
              </tr>
            </thead>
            <tbody>
              {students.map((student) => (
                <tr key={student.id}>
                  <td>{student.name}</td>
                  <td>{student.surname}</td>
                  <td>{student.tckn}</td>
                  <td>{student.studentNo}</td>
                  <td>{student.grade}</td>
                  <td>{student.phoneNumber}</td>
                  <td>{student.birthPlace}</td>
                  <td>{new Date(student.birthDate).toLocaleDateString()}</td>
                  <td>{student.faculty.name}</td>
                  <td>{student.address}</td>
                  <td>
                  <button className="action-button" onClick={() => handleDelete(student.id)}>
                  <FontAwesomeIcon icon={faTrash} /> Sil </button>
                  <button className="action-button" onClick={() => handleUpdate(student.id)}>
                  <FontAwesomeIcon icon={faEdit} /> Güncelle </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <br></br>
        <button className="previous" onClick={handlePreviousPage} disabled={currentPage === 0} style={{ fontSize: '24px', color: 'blue', marginRight: '20px',background: currentPage === 0 ? 'gray' : 'inherit',
        cursor: currentPage === 0 ? 'not-allowed' : 'pointer',}}>
        <FontAwesomeIcon icon={faChevronLeft} /> Önceki
        </button>
        <button className="next" onClick={handleNextPage} disabled={currentPage === totalPages - 1} style={{ fontSize: '24px', color: 'blue', marginLeft: '900px',background: currentPage === totalPages - 1 ? 'gray' : 'inherit',
        cursor: currentPage === totalPages - 1 ? 'not-allowed' : 'pointer', }}>
         Sonraki <FontAwesomeIcon icon={faChevronRight} />
        </button>
        {showUpdateModal && (
                <div className="fixed inset-0 flex items-center justify-center z-50">
                    <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
                    <div className="bg-white rounded-lg p-8 z-50">
                        <StudentUpdate _studentId={selectedStudentId} isOpen={showUpdateModal} onClose={closeUpdateModal} />
                        <button
                            className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
                            onClick={closeUpdateModal}
                        >
                            Kapat
                        </button>
                    </div>
                </div>
            )}
        {showAddModal && (
                <div className="fixed inset-0 flex items-center justify-center z-50">
                    <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
                    <div className="bg-white rounded-lg p-8 z-50">
                        <StudentAdd isOpen={showAddModal} onClose={closeAddModal} />
                        <button className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
                        onClick={closeAddModal}>Kapat</button>
                    </div>
                </div>
            )}
        </div>
        
      );
  };
export default StudentList;
