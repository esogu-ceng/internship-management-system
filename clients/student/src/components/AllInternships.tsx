import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Internship } from '../types/InternshipType';
import Modal from '../components/CompanyInfo';
import InternshipDocument from '../components/InternshipDocuments'


function AllInternships() {
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [selectedCompanyId, setSelectedCompanyId] = useState<number | null>(null);
    const [selectedInternshipId, setSelectedInternshipId] = useState<number | any>();
    const [showDocumentModal, setDocumentShowModal] = useState<boolean>(false);
    const [internships, setInternships] = useState<any[]>([]);

    const openModal = (companyId: number | null) => {
        setSelectedCompanyId(companyId);
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
    };

    const openDocumentModal = (internshipId: number | null) => {
        setSelectedInternshipId(internshipId);
        setDocumentShowModal(true);
    };

    const closeDocumentModal = () => {
        setDocumentShowModal(false);
    };

    useEffect(() => {
        fetch('/api/user/student/auth', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                console.log('data: ', data);
                const fetchedUserId = data.id;

                fetch(`/api/student/byUserId/${fetchedUserId}`, {
                    method: 'GET'
                })
                    .then(response => response.json())
                    .then(studentData => {
                        console.log('studentData: ', studentData);
                        const studentId = studentData.id;

                        fetch(`/api/internship/student/${studentId}`, {
                            method: 'GET'
                        })
                            .then(response => response.json())
                            .then(internshipData => {
                                console.log('internshipData: ', internshipData);
                                setLoading(false);

                                Promise.all(
                                    internshipData.content.map((internship: any) => {
                                        const fetchFacultySupervisor = fetch(`/api/facultySupervisor/${internship.facultySupervisorId}`, {
                                            method: 'GET'
                                        }).then(response => response.json());

                                        const fetchCompany = fetch(`/api/company/${internship.companyId}`, {
                                            method: 'GET'
                                        }).then(response => response.json());

                                        return Promise.all([fetchFacultySupervisor, fetchCompany])
                                            .then(([facultySupervisorData, companyData]) => {
                                                console.log('facultySupervisorData: ', facultySupervisorData);
                                                console.log('companyData: ', companyData);

                                                internship.facultySupervisorName = facultySupervisorData.name + " " + facultySupervisorData.surname;
                                                internship.companyName = companyData.name;

                                                return internship;
                                            })
                                            .catch(error => {
                                                console.log("Fakülte sorumlusu veya şirket bilgilerine ulaşılamadı");
                                                return internship;
                                            });
                                    })
                                )
                                    .then((updatedInternships) => {
                                        setInternships(updatedInternships);
                                    })
                                    .catch(error => {
                                        console.log("Staj bilgilerine ulaşılamadı");
                                    });
                            })
                            .catch(error => {
                                console.log("Staj bilgilerine ulaşılamadı");
                            });
                    })
                    .catch(error => {
                        console.log("Öğrenci bilgilerine ulaşılamadı");
                    });
            })
            .catch(error => {
                console.log("Kullanıcı oturumu bilgilerine ulaşılamadı");
            });
    }, []);


    if (loading) {
        return <div>Loading...</div>;
    }

    if (!internships || internships.length === 0) {
        return <div>No internships found.</div>;
    }

    return (
        <div className="bg-white p-5 rounded-md w-full pt-0">
            <div className="flex items-center justify-between pb-6">
                <div>
                    <h1 className="font-bold text-4xl font-semibold mb-4">Stajlarım</h1>
                    <span className="text-md text-black">
                        Tüm
                        <span className=" font-semibold text-green-500"> "Onaylı", </span>
                        <span className="font-semibold text-gray-500'> ">  "Beklemede" </span>
                        ve
                        <span className=" font-semibold text-red-500"> "Reddedilmiş" </span> stajlar
                    </span>
                </div>
            </div>

            <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto mt-2">
                <div className="inline-block min-w-full shadow rounded-lg overflow-hidden">
                    <table className="min-w-full leading-normal">
                        <thead>
                            <tr>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    ÖĞRENCİ ADI
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    ŞİRKET ADI
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    BAŞLANGIÇ TARİHİ
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    BİTİŞ TARİHİ
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    TOPLAM GÜN
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    STAJ DURUMU
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    FAKÜLTE SORUMLUSU
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 ">
                                    STAJ BELGELERİ
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            {internships.map((internship: Internship) => (
                                <tr key={internship.id}>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Öğrenci Adı */}
                                        <div className="flex items-center">
                                            {/* Render student name */}
                                            <div className="ml-3">
                                                <p className="text-gray-900 whitespace-no-wrap">{internship.student.name} {internship.student.surname}</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm" onClick={() => openModal(internship.companyId)}>
                                        {/* Şirket Adı */}
                                        <p className="border border-blue-500 text-blue-500 rounded-md px-4 py-2 transition duration-300 ease-in-out hover:bg-blue-500 hover:text-white cursor-pointer"> {internship.companyName}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Başlangıç Tarihi */}
                                        <p className="text-gray-900 whitespace-no-wrap">{new Date(internship.startDate).toLocaleDateString()}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Bitiş Tarihi */}
                                        <p className="text-gray-900 whitespace-no-wrap">{new Date(internship.endDate).toLocaleDateString()}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Toplam Gün */}
                                        <p className="text-gray-900 whitespace-no-wrap">{internship.days}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Staj Durumu */}
                                        <span className={`relative inline-block px-3 py-1 font-semibold leading-tight 
                                              ${internship.status === "PENDING" ? "text-gray-500" :
                                                internship.status === "APPROVED" ? "text-green-500" :
                                                    internship.status === "REJECTED" ? "text-red-500" : ""}`}>
                                            <span
                                                aria-hidden
                                                className={`absolute inset-0 
                                             ${internship.status === "PENDING" ? "bg-gray-200" :
                                                        internship.status === "APPROVED" ? "bg-green-200" :
                                                            internship.status === "REJECTED" ? "bg-red-200" : ""} opacity-50 rounded-full`}
                                            ></span>
                                            {internship.status === "PENDING" ? "BEKLEMEDE" :
                                                internship.status === "APPROVED" ? "ONAYLI" :
                                                    internship.status === "REJECTED" ? "REDDEDİLDİ" : ""}
                                        </span>
                                    </td>

                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Fakülte Sorumlusu */}
                                        <p className="text-gray-900 whitespace-no-wrap">{internship.facultySupervisorName}</p>
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
            {showModal && (
                <div className="fixed inset-0 flex items-center justify-center z-50">
                    <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
                    <div className="bg-white rounded-lg p-8 z-50">
                        <Modal _company={selectedCompanyId} isOpen={showModal} onClose={closeModal} />
                        <button
                            className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
                            onClick={closeModal}
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
