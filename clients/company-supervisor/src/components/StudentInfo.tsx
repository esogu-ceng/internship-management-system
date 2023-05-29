import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Student } from '../types/StudentType';

type ModalProps = {
  _student: Student | undefined;
  isOpen: boolean;
  onClose: () => void;
  children?: React.ReactNode;
};

const StudentInfo: React.FC<ModalProps> = ({ _student, isOpen, onClose, children }) => {
  const [student, setStudent] = useState<(Student) | undefined>(_student);
  const [loading, setLoading] = useState<boolean>(true);


  useEffect(() => {
    console.log("student", JSON.stringify(student));
    setLoading(false);
  }, []);

  const SGKCheck = (value: boolean | undefined) => {
    if (value) {
      return ("Var");
    }
    else if (!value) {
      return ("Yok");
    }
    else
      return ("Bilinmiyor");
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!student) {
    return <div>No students found.</div>;
  }

  return (
    <div className="bg-white p-5 rounded-md w-full pt-0">
      <h2 style={{ textAlign: 'center', fontWeight: 'bold', color: '#3A4F7A' }}>ÖĞRENCİ BİLGİLERİ</h2>
      <div style={{ maxHeight: '80vh', overflowY: 'auto', }}>
        <br />
        <div style={{ width: '100%', overflowX: 'auto' }}>
          <table style={{ tableLayout: 'auto' }} className="min-w-full leading-normal">
            <thead>
              <tr key={student.id}>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    ÖĞRENCİ ADI
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.name} {student.surname}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    TC KİMLİK NUMARASI
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.tckn}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    ÖĞRENCİ NUMARASI
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.studentNo}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    NOT
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.grade}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    İLETİŞİM NUMALARI
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    <strong style={{ color: '#3A4F7A' }}>Cep Telefonu: </strong>{student.phoneNumber}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Ev Telefonu: </strong>{student.homePhoneNumber}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    ADRES
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.boulevard}, {student.mainStreet}, {student.street}, {student.neighborhood}, {student.village},
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Dış Kapı Numarası: </strong> {student.outerDoorNo},<strong style={{ color: '#3A4F7A' }}> İç Kapı Numarası:</strong>  {student.innerDoorNo}
                    <br />
                    {student.subprovince} / {student.province}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Posta Kodu:</strong>{student.zipCode}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    ANNE ADI
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.motherName}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    BABA ADI
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.fatherName}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    DOĞUM YERİ
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {student.birthPlace}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    DOĞUM TARİHİ
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {new Date(student.birthDate).toLocaleDateString('en-GB')}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    KİMLİK BİLGİLERİ
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    <strong style={{ color: '#3A4F7A' }}>Kimlik Kartı Seri Numarası: </strong>{student.idCardSerialNo}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Aile Seri Numarası:</strong>  {student.idRegisterFamilySerialNo}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Sıra Numarası: </strong> {student.idRegisterVolumeNo}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Nüfusa Kayıtlı İl:</strong> {student.idRegisterProvince}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Nüfusa Kayıtlı İlçe:</strong> {student.idRegisterSubprovince}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Nüfusa Kayıtlı Köy:</strong> {student.idRegisterStreetVillage}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}>Kayıt Ofisi: </strong> {student.idRegistryOffice}
                    <br />
                    <strong style={{ color: '#3A4F7A' }}> Kayıt Sebebi: </strong> {student.idRegistryReason}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    AİLE SGK DURUMU
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                    {SGKCheck(student.sgkFamily)}
                  </td>
                </tr>
                <tr>
                  <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                    style={{ color: '#3A4F7A' }}>
                    ÖĞRENCİ SGK DURUMU
                  </th>
                  <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm"
                    style={{ color: '#3A4F7A' }}>
                    {SGKCheck(student.sgkSelf)}
                  </td>
                </tr>
              </tr>
            </thead>
          </table>
        </div>
      </div>
    </div>
  )
};

export default StudentInfo;
