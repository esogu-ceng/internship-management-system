import React, { useEffect, useState } from 'react';
import { Internship } from '../types/InternshipType';
import { Student } from '../types/StudentType';
import axios from 'axios';
import CompanyInfo from '../components/CompanyInfo';
import StudentInfo from '../components/StudentInfo';
import InternshipDocument from '../components/InternshipDocuments'
import InternshipStatusChange from '../components/InternshipStatusChange';


interface PageableResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
}

function AllInternships({ _facultySupervisorId }: { _facultySupervisorId: number }) {
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(10);
  const [totalPages, setTotalPages] = useState<number>(0);
  const [sortBy, setSortBy] = useState<string>('id');
  const [internships, setInternships] = useState<Internship[] | any>([]);
  const [showCompanyModal, setCompanyShowModal] = useState<boolean>(false);
  const [selectedCompanyId, setSelectedCompanyId] = useState<number | null>(null);
  const [showStudentModal, setStudentShowModal] = useState<boolean>(false);
  const [selectedStudent, setSelectedStudent] = useState<Student | any>();
  const [selectedInternshipId, setSelectedInternshipId] = useState<number | any>();
  const [showDocumentModal, setDocumentShowModal] = useState<boolean>(false);
  const [statusChanged, setStatusChanged] = useState<boolean>(false);
  const openDocumentModal = (internshipId: number | null) => {
    setSelectedInternshipId(internshipId);
    setDocumentShowModal(true);
  };

  const closeDocumentModal = () => {
    setDocumentShowModal(false);
  };

  const openStudentModal = (student: Student | null) => {
    setSelectedStudent(student);
    setStudentShowModal(true);
  };

  const closeStudentModal = () => {
    setStudentShowModal(false);
  };

  const openCompanyModal = (companyId: number | null) => {
    setSelectedCompanyId(companyId);
    setCompanyShowModal(true);
  };

  const closeCompanyModal = () => {
    setCompanyShowModal(false);
  };

  const fetchInternship = async (page: number, size: number, sort: string) => {
    try {
      const response = await axios.get(`/api/internship/supervisor/${_facultySupervisorId}`, {
        params: {
          pageNo: page,
          limit: size,
          sortBy: sort,
        },

      });
      console.log(response);
      const { content, totalPages } = response.data as PageableResponse<Internship>;
    
      setInternships(content);
      setTotalPages(totalPages);
    } catch (error) {
      console.error(error);
    }
  }

  useEffect(() => {
    fetchInternship(currentPage, pageSize, sortBy);
  }, [currentPage, pageSize, sortBy]);
  useEffect(() => {
    if (statusChanged) {
      fetchInternship(currentPage, pageSize, sortBy);
      setStatusChanged(false);
    }
  }, [statusChanged, currentPage, pageSize, sortBy]);
  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => prevPage - 1);
  };

  const handleNextPage = () => {
    setCurrentPage((prevPage) => prevPage + 1);
  };
  const handleStatusChange = () => {
    setStatusChanged(true);
  };
  if (!internships || internships.length === 0) {
    return <div>No internships found.</div>;
  }

  const getStatusColor = (status: string) => {
    if (status === 'APPROVED') {
      return 'bg-green-200 p-20 rounded';
    } else if (status === 'REJECTED') {
      return 'bg-red-200 p-20 rounded';
    } else if (status === 'PENDING') {
      return 'bg-yellow-200 p-20 rounded';
    }
    return '';
  };
  const handleInternshipStatusChange = () => {
    fetchInternship(currentPage, pageSize, sortBy);
  };

  

  return (
    <div className="bg-white p-5 rounded-md w-full pt-0">
      <div className="flex items-center justify-between pb-6">
        <div>
          <h1 className="font-bold text-4xl font-semibold mb-4">Stajlar</h1>
          <span className="text-md text-black">
            Tüm
            <span className=" font-semibold text-green-500"> "Onaylı", </span>
            <span className="font-semibold text-yellow-500">  "Beklemede" </span>
            ve
            <span className=" font-semibold text-red-500"> "Reddedilmiş" </span> stajlar
          </span>
        </div>
      </div>
      <div className="flex items-center justify-between">
        <div className="flex items-center justify-between mt-4">
          <button
            className="px-4 py-2 bg-gray-200 rounded-md mr-2"
            onClick={handlePreviousPage}
            disabled={currentPage === 0}
          >
            Önceki Sayfa
          </button>
          <button
            className="px-4 py-2 bg-gray-200 rounded-md"
            onClick={handleNextPage}
            disabled={currentPage === totalPages - 1}
          >
            Sonraki Sayfa
          </button>
        </div>
      </div>
      <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
        <div className="inline-block min-w-full shadow rounded-lg overflow-hidden">
          <table className="min-w-full leading-normal">
            <thead>
              <tr>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Fakülte Sorumlusu
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Öğrenci Adı
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Şirket Adı
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Başlangıç Tarihi
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Bitiş Tarihi
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Toplam Gün
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Staj Durumu
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Staj Belgeleri
                </th>
              </tr>
            </thead>
            <tbody>
              {internships.map((internship: Internship) => (
                <tr key={internship.id}>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    <div className="flex items-center">
                      <div className="ml-3">
                        <p className="text-gray-900 whitespace-no-wrap">{internship.facultySupervisor.name + " " + internship.facultySupervisor.surname}</p>
                      </div>
                    </div>
                  </td>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm" onClick={() => openStudentModal(internship.student)}>
                    <p className="border border-blue-500 text-blue-500 rounded-md px-4 py-2 transition duration-300 ease-in-out hover:bg-blue-500 hover:text-white cursor-pointer text-center">
                      <span className="inline-block w-40 mx-auto">
                        {internship.student.name + " " + internship.student.surname}
                      </span>
                    </p>
                  </td>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm" onClick={() => openCompanyModal(internship.company.id)}>
                    <p className="border border-blue-500 text-blue-500 rounded-md px-4 py-2 transition duration-300 ease-in-out hover:bg-blue-500 hover:text-white cursor-pointer text-center">
                      <span className="inline-block w-40 mx-auto">
                        {internship.company.name}
                      </span>
                    </p>
                  </td>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    <p className="text-gray-900 whitespace-no-wrap">{new Date(internship.startDate).toLocaleDateString('en-GB')}</p>
                  </td>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    <p className="text-gray-900 whitespace-no-wrap">{new Date(internship.endDate).toLocaleDateString('en-GB')}</p>
                  </td>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    <p className="text-gray-900 whitespace-no-wrap">{internship.days}</p>
                  </td>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    <span
                      className={`relative inline-block px-3 py-1 font-semibold leading-tight ${getStatusColor(internship.status)
                        }`}
                    >
                      <span aria-hidden className="absolute inset-0 opacity-50 rounded-full"></span>
                      <span className="relative">{internship.status}</span>
                      
                    </span>
                    <span>
                      
      <InternshipStatusChange id={internship.id} onStatusChange={handleStatusChange}   />
    </span>
                  </td>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {/* Staj Belgeleri */}
                    <button
                      className="border border-blue-500 text-blue-500 rounded-md px-4 py-2 transition duration-300 ease-in-out hover:bg-blue-500 hover:text-white"
                      onClick={() => openDocumentModal(internship.id)}
                    >
                      Görüntüle
                    </button>
                  </td>
                  
                </tr>
                
              ))}
               
            </tbody>
          </table>
        </div>
      </div>
      {showCompanyModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
          <div className="bg-white rounded-lg p-8 z-50">
            <CompanyInfo _company={selectedCompanyId} isOpen={showCompanyModal} onClose={closeCompanyModal} />
            <button
              className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
              onClick={closeCompanyModal}
            >
              Kapat
            </button>
          </div>
        </div>
      )}
      {showStudentModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
          <div className="bg-white rounded-lg p-8 z-50">
            <StudentInfo _student={selectedStudent} isOpen={showStudentModal} onClose={closeStudentModal} />
            <button
              className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
              onClick={closeStudentModal}
            >
              Kapat
            </button>
          </div>
        </div>
      )}
      
      {showDocumentModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
          <div className="bg-white rounded-lg p-8 z-50">
            <InternshipDocument _internshipId={selectedInternshipId} isOpen={showDocumentModal} onClose={closeDocumentModal} />
            <button
              className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
              onClick={closeDocumentModal}
            >
              Kapat
            </button>
          </div>
        </div>
        
      )}
      
    </div>
  );
}

export default AllInternships;