import React, { useEffect, useState, ReactNode } from 'react';
import { Internship } from '../types/InternshipType';
import Modal from '../components/StudentInfo';

export const InternshipRow = ({ internship }: { internship: Internship }) => {
  const [hoveredButton, setHoveredButton] = useState<string | null>(null);
  const [popUpScreen, setPopUpScreen] = useState<ReactNode>(<></>);
  const [popUpState, setPopUpState] = useState<boolean>(false);

  const handleButtonHover = (buttonName: string | null) => {
    setHoveredButton(buttonName);
  };
  const handleStudentInfo = () => {
    setPopUpState(true);
    console.log("Running handleStudentInfo");
    setPopUpScreen(<Modal _student={internship.student} isOpen={popUpState} onClose={onClosePopUp}></Modal>);
  };
  const handleInternshipBooks = () => {
    setPopUpState(true);
    console.log("Running handleInternshipBooks");
    setPopUpScreen(<h2>STAJ BİLGİLERİ</h2>);
  };
  const handleCompanyEvaluation = () => {
    setPopUpState(true);
    console.log("Running handleCompanyEvaluation");
    setPopUpScreen(<h2>ŞİRKET DEĞERLENDİRMESİ</h2>);
  };
  const onClosePopUp = () => {
    setPopUpState(false);
  };

  const buttonHoverPopUp = (hoverText: string | null) => {
    return (
      <>
        <svg className="hidden md:inline-block ml-1 absolute top-4 left-1/2 -translate-x-1/2 mt-2 " viewBox="0 0 20 20" >
          <path d="M10 0L20 20H0L10 0Z" fill=" bg-indigo-500" />
        </svg>
        <span
          className={`hidden md:inline-block ml-1 absolute top-8 left-1/2 -translate-x-1/2 bg-indigo-500 py-1 px-2 rounded-md text-xs text-white whitespace-no-wrap transition-opacity duration-300 opacity-100`}
          style={{ top: 'calc(100% + 8px)' }}
        >
          {hoverText}
        </span>
      </>
    );
  };

  return (
    <tr>
      <td className="px-5 py-5 text-sm bg-white border-b border-gray-200">
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
        <div className="flex space-x-3">
          <button
            className="text-indigo-600 hover:text-indigo-900 relative"
            onMouseEnter={() => handleButtonHover('studentInfo')}
            onMouseLeave={() => handleButtonHover(null)}
            onClick={() => handleStudentInfo()}
          >
            <svg className="w-6 h-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <circle cx="12" cy="7" r="4"></circle>
              <path d="M2 20C2 14.4772 6.47715 10 12 10C17.5228 10 22 14.4772 22 20"></path>
            </svg>
            {hoveredButton == 'studentInfo' && (
              buttonHoverPopUp("Öğrenci Bilgileri")
            )}
          </button>
          <button
            className="text-indigo-600 hover:text-indigo-900 relative"
            onMouseEnter={() => handleButtonHover('internshipBooks')}
            onMouseLeave={() => handleButtonHover(null)}
            onClick={() => handleInternshipBooks()}
          >
            <svg className="w-6 h-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M9 19V5H21V19H9Z"></path>
              <path d="M9 3L9 19"></path>
              <path d="M21 3L9 3"></path>
              <path d="M3 3L3 19"></path>
            </svg>
            {hoveredButton == 'internshipBooks' && (
              buttonHoverPopUp("Staj Defteri")
            )}

          </button>
          <button
            className="text-indigo-600 hover:text-indigo-900 relative"
            onMouseEnter={() => handleButtonHover('companyEvaluation')}
            onMouseLeave={() => handleButtonHover(null)}
            onClick={() => handleCompanyEvaluation()}
          >
            <svg className="w-6 h-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M22 11.07V12a10 10 0 1 1-5.93-9.14"></path>
              <polyline points="22 4 12 14.01 9 11.01"></polyline>
            </svg>
            {hoveredButton == 'companyEvaluation' && (
              buttonHoverPopUp("Şirket Değerlendirmesi")
            )}
          </button>
        </div>
      </td>
      {popUpState && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
          <div className="bg-white rounded-lg p-8 z-50 flex flex-col items-center">
            {popUpScreen}
            <button
              className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
              onClick={onClosePopUp}
            >
              Kapat
            </button>
          </div>
        </div>
      )}
    </tr>
  );
};
