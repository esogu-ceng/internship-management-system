import React, { useEffect, useState } from 'react';
import { Internship } from '../types/InternshipType';
import Modal from '../components/StudentInfo';


export const InternshipRow = ({ internship }: { internship: Internship }) => {
  const [showModal, setShowModal] = useState<boolean>(false);

  const openModal = () => {
    console.log("blablka"); //deneme
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  return (
    <tr>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200"
        onClick={openModal}>
        <p className="text-gray-900 whitespace-no-wrap">
          {internship.student.name}
        </p>
      </td>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200">
        <p className="text-gray-900 whitespace-no-wrap">
          {internship.student.surname}
        </p>
      </td>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200">
        <p className="text-gray-900 whitespace-no-wrap">{internship.days}</p>
      </td>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200">
        <p className="text-gray-900 whitespace-no-wrap">
          {new Date(internship.startDate).toLocaleDateString('en-GB')}
        </p>
      </td>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200">
        <p className="text-gray-900 whitespace-no-wrap">
          {new Date(internship.endDate).toLocaleDateString('en-GB')}
        </p>
      </td>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200">
        <span className="relative inline-block px-3 py-1 font-semibold leading-tight text-green-900">
          <span
            aria-hidden="true"
            className="absolute inset-0 bg-green-200 rounded-full opacity-50"
          ></span>
          <span className="relative">{internship.status}</span>
        </span>
      </td>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200">
        <a href="#" className="text-indigo-600 hover:text-indigo-900">
          Edit
        </a>
      </td>
      {showModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
          <div className="bg-white rounded-lg p-8 z-50 flex flex-col items-center"> {/* Add flex and items-center classes */}
            <h2>ÖĞRENCİ BİLGİLERİ</h2>
            <p>
              <Modal _student={internship.student} isOpen={showModal} onClose={closeModal}></Modal>
            </p>
            <button
              className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
              onClick={closeModal}
            >
              Kapat
            </button>
          </div>
        </div>
      )}
    </tr>
  );
};
