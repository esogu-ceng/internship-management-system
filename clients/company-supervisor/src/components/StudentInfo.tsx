import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Student } from '../types/StudentType';

type ModalProps = {
    _student : Student|undefined;
    isOpen: boolean;
    onClose: () => void;
    children?: React.ReactNode;
};

const StudentInfo: React.FC<ModalProps> = ({ _student, isOpen, onClose, children }) => {
    const [student, setStudent] = useState<(Student) | undefined>(_student);
    const [loading, setLoading] = useState<Boolean>(true);

    
    useEffect(() => {
       console.log("student", JSON.stringify(student));
        setLoading(false);
    }, []);

    const SGKCheck = (value:boolean|undefined) => {
            if(value){
                return("Var");
            }
            else if(!value){
                return("Yok");
            }
            else
                return("Bilinmiyor");
      };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!student) {
        return <div>No students found.</div>;
    }

    return (
        <div className="bg-white p-5 rounded-md w-full pt-0">
            <table className="min-w-full leading-normal">
                <thead>
                    <tr key={student.id}> 
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Öğrenci Adı
                      </th>
                        <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {student.name} {student.surname} 
                        </td>
                    </tr>
                    <tr>
                        <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            TC Kimlik Numarası
                        </th>
                        <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {student.tckn}
                        </td>
                    </tr>
                    <tr>
                        <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Öğrenci Numarası
                        </th>
                        <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {student.studentNo}
                        </td>
                    </tr>
                    <tr>
                        <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Not
                        </th>
                        <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {student.grade}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        İletişim  Numaraları
                        </th>
                        <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            Cep Telefonu: {student.phoneNumber}
                            <br />
                            Ev Telefonu: {student.homePhoneNumber}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Adres
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                        {student.boulevard},
                                {student.mainStreet},{student.street},
                                {student.neighborhood}, {student.village},
                                <br />
                                Dış Kapı Numarası: {student.outerDoorNo}, İç Kapı Numarası: {student.innerDoorNo},  Posta Kodu: {student.zipCode}
                                <br />
                                {student.subprovince} / {student.province}                               
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Anne Adı
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {student.motherName}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Baba Adı
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {student.fatherName}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Doğum Yeri
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {student.birthPlace}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Doğum Tarihi
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {new Date(student.birthDate).toLocaleDateString('en-GB')}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Kimlik Bilgileri
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            Sıra Numarası: {student.idRegisterVolumeNo}, Seri Numarası:{student.idCardSerialNo} , Aile Seri Numarası:  {student.idRegisterFamilySerialNo}
                            <br /> 
                            Nüfusa Kayıtlı İl, İlçe ve Köy: {student.idRegisterProvince}, {student.idRegisterSubprovince}, {student.idRegisterStreetVillage}
                            <br />
                            Kayıt Ofisi: {student.idRegistryOffice},
                            Kayıt Sebebi: {student.idRegistryReason}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Aile SGK Durumu
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {SGKCheck(student.sgkFamily)}
                        </td>
                    </tr>
                    <tr>
                      <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Öğrenci SGK Durumu
                      </th>
                      <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                            {SGKCheck(student.sgkSelf)}
                        </td>
                    </tr>
                    </tr>
                </thead>
            </table>
        </div>
)};

export default StudentInfo;
