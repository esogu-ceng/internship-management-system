import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Internship } from '../types/InternshipType';
import Modal from '../components/CompanyInfo';
import InternshipDocument from '../components/InternshipDocuments'


function AllInternships() {
    const [internships, setInternships] = useState<Internship[] | null>(null);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [selectedCompanyId, setSelectedCompanyId] = useState<number | null>(null);
    const [selectedInternshipId, setSelectedInternshipId] = useState<number | any>();
    const [showDocumentModal, setDocumentShowModal] = useState<boolean>(false);

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
        fetch('/api/internship/1015', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                console.log('data: ', data);
                const internship = {
                    ...data,
                    startDate: new Date(data.startDate),
                    endDate: new Date(data.endDate)
                };
                setInternships([internship]);
                setLoading(false);
            })
            .catch(error => {
                console.log(error);
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
                        <span className="font-semibold text-gray-600'> ">  "Beklemede" </span>
                        ve
                        <span className=" font-semibold text-red-500"> "Reddedilmiş" </span> stajlar
                    </span>
                </div>
            </div>
            <div className="flex items-center justify-between">
                <div className="flex bg-gray-50 items-center p-2 rounded-md">
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-5 w-5 text-gray-400"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                    >
                        <path
                            fillRule="evenodd"
                            d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                            clipRule="evenodd"
                        />
                    </svg>
                    <input className="bg-gray-50 outline-none ml-1 block" type="text" placeholder="Search..." />
                </div>
            </div>
            <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                <div className="inline-block min-w-full shadow rounded-lg overflow-hidden">
                    <table className="min-w-full leading-normal">
                        <thead>
                            <tr>
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
                                    Fakülte Sorumlusu
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
                                        <p className="border border-blue-500 text-blue-500 rounded-md px-4 py-2 transition duration-300 ease-in-out hover:bg-blue-500 hover:text-white cursor-pointer"> {internship.companyId}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Başlangıç Tarihi */}
                                        <p className="text-gray-900 whitespace-no-wrap">{internship.startDate.toLocaleDateString()}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Bitiş Tarihi */}
                                        <p className="text-gray-900 whitespace-no-wrap">{internship.endDate.toLocaleDateString()}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Toplam Gün */}
                                        <p className="text-gray-900 whitespace-no-wrap">{internship.days}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Staj Durumu */}
                                        <span className="relative inline-block px-3 py-1 font-semibold text-green-900 leading-tight">
                                            <span
                                                aria-hidden
                                                className="absolute inset-0 bg-green-200 opacity-50 rounded-full"
                                            ></span>
                                            <span className="relative">{internship.status}</span>
                                        </span>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Fakülte Sorumlusu */}
                                        <p className="text-gray-900 whitespace-no-wrap">{internship.facultySupervisorId}</p>
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
