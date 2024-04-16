import React, { Component, useState } from 'react';
type ModalProps = {
  isOpen: boolean;
  onClose: () => void;
  children?: React.ReactNode;
};
export const StudentAdd : React.FC<ModalProps> = ({isOpen, onClose, children }) => {
  const [student, setStudent] = useState({
    name: '',
    surname: '',
    tckn: '',
    grade: '',
    studentNo: '',
    phoneNumber: '',
    birthPlace: '',
    birthDate:  new Date(""),
    user: {
      id: 0,
      email: ""
    },
    faculty:{
      id:0,
      name: ""
    },
    address: ''
  });

  const handleAddStudent = () => { };

  if (!isOpen) {
    return null;
  }

    return (
      <div className="student">
        <div>
        <tr>
        <td>İsim:</td>
        <td>
          <input type="text" name="name" value={student.name}/>
        </td>
    </tr>
    <tr>
        <td>Soyadı:</td>
        <td>
          <input type="text" name="surname" value={student.surname}/>
        </td>
      </tr>
      <tr>
        <td>TCKN:</td>
        <td>
          <input type="text" name="tckn" value={student.tckn}/>
        </td>
      </tr>
      <tr>
        <td>Öğrenci Numarası:</td>
        <td>
          <input type="text" name="studentNo" value={student.studentNo}/>
        </td>
      </tr>
      <tr>
        <td>Ortalama:</td>
        <td>
          <input type="text" name="grade" value={student.grade}/>
        </td>
      </tr>
      <tr>
        <td>Telefon Numarası:</td>
        <td>
          <input type="text" name="phoneNumber" value={student.phoneNumber}/>
        </td>
      </tr>
      <tr>
        <td>Doğum yeri:</td>
        <td>
          <input type="text" name="birthPlace" value={student.birthPlace}/>
        </td>
      </tr>
      <tr>
        <td>Doğum Tarihi:</td>
        <td>
          <input type="date" name="birthDate" value={new Date(student.birthDate).toLocaleDateString('en-CA')} />
        </td>
      </tr>
      <tr>
        <td>Adres:</td>
        <td>
          <input type="text" name="address" value={student.address}/>
        </td>
      </tr>
      <button
       className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
       onClick={handleAddStudent}>Ekle</button>
        </div>
      </div>
    );
};