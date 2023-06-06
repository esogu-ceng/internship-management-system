import { useEffect, useState } from 'react';
import { Student } from '../../types/StudentListType';
import axios from 'axios';
type ModalProps = {
    _studentId: number| null;
    isOpen: boolean;
    onClose: () => void;
    children?: React.ReactNode;
  };
export const StudentUpdate : React.FC<ModalProps> = ({ _studentId, isOpen, onClose, children }) => {
    const [student, setStudent] = useState<Student | null>({
        id: 0,
        name: "",
        surname: "",
        tckn: "",
        studentNo: "",
        grade: "",
        phoneNumber: "",
        birthPlace: "",
        birthDate:  new Date(""),
        user: {
            id: 0,
            email: ""
          },
          faculty:{
            id:0,
            name: ""
          },
        address: ""
      });
    const [totalPages, setTotalPages] = useState<number>(0);
    useEffect(() => {
        const fetchStudentShowById = async () => {
            try {
              const response = await axios.get(`/api/student/${_studentId}`);
              const { data } = response;
              const { content, totalPages } = data;
              setStudent(data);
            } catch (error) {
              console.error(error);
              throw error;
            }
        };
        fetchStudentShowById();
     }, [_studentId]);
    if (!isOpen || !_studentId) {
        return null; 
      }    
    const updateStudent = async () => { };
    return ( 
    <div className="student">
    {student ? (
    <div>
    <tr>
        <td>İsim:</td>
        <td>
          <input type="text" name="name" value={student.name} />
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
          <input type="text" name="studentNo" value={student.studentNo} />
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
    <br></br>
    <button
    className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
    onClick={updateStudent}>Güncelle</button>
  </div>
    ) : (
      <div>Öğrenci bulunamadı</div>
    )}
   </div>
   );
};
