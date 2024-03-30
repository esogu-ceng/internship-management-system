import useStudentManagement from "../hooks/useStudentManagement";

import AddModalForm from "./StudentAddModalForm";
import UpdateModalForm from "./StudentUpdateModalForm";
import Pagination from "./Pagination";

const tableHeaders = [
  "Ad",
  "Soyad",
  "T.C. Kimlik No",
  "Öğrenci No",
  "Not Ortalaması",
  "Fakülte",
  "Telefon No",
  "Doğum Yeri",
  "Doğum Tarihi",
  "Adres",
  "Kullanıcı Adı",
  "Email",
  "Aktiflik Durumu",
  "Düzenle/Sil",
];

const StudentsPage = () => {
  const {
    isAddModalOpen,
    setIsAddModalOpen,
    addStudent,
    isUpdateModalOpen,
    selectedStudent,
    updateStudent,
    deleteStudent,
    setIsUpdateModalOpen,
    students,
    pagination,
    setSelectedStudent,
    pageChangeHandler,
    faculties,
    handleCheckboxChange,
  } = useStudentManagement();

  const handleOpenModal = () => {
    setIsAddModalOpen(true);
    document.body.style.overflow = "hidden";
  };

  const handleOnClose = () => {
    setIsUpdateModalOpen(false);
    document.body.style.overflow = "auto";
  };

  const handleDeleteUser = (id: number) => {
    const confirmDelete = window.confirm("Öğrenci kaydını silmek istediğinden eminmisiniz");
    if(confirmDelete){
      deleteStudent(id);
    }
  };

  return (
    <div className="container">
      {isAddModalOpen && (
        <AddModalForm
          showModal={isAddModalOpen}
          onShowModal={setIsAddModalOpen}
          faculties={faculties}
          onAddStudents={addStudent}
        />
      )}
      {isUpdateModalOpen && selectedStudent && (
        <UpdateModalForm
          studentDto={selectedStudent}
          onUpdateStudent={updateStudent}
          onClose={handleOnClose}
          faculties={faculties}
        />
      )}
      <button className="add-button" onClick={handleOpenModal}>
        <span>+ Öğrenci Ekle</span>
      </button>

      <table>
        <thead>
          <tr>
            {tableHeaders.map((header) => (
              <th key={header}>{header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {students?.map((student) => (
            <tr key={student.id}>
              <td>{student.name}</td>
              <td>{student.surname}</td>
              <td>{student.tckn}</td>
              <td>{student.studentNo}</td>
              <td>{student.grade}</td>
              <td>{student.faculty.name}</td>
              <td>{student.phoneNumber}</td>
              <td>{student.birthPlace}</td>
              <td>
                {new Date(student.birthDate)
                  .toLocaleDateString("en-GB")
                  .split("/")
                  .join("-")}
              </td>
              <td>{student.address}</td>
              <td>{student.user.username}</td>
              <td>{student.user.email}</td>
              <td>
                <input
                  type="checkbox"
                  checked={student.user.activity}
                  onChange={() => handleCheckboxChange(student.id)}
                />
              </td>
              <td>
                <div className="update-buttons">
                  <button
                    onClick={() => {
                      setSelectedStudent(student);
                      setIsUpdateModalOpen(true);
                      document.body.style.overflow = "hidden";
                    }}
                  >
                    Düzenle
                  </button>{" "}
                  <button onClick={() => handleDeleteUser(student.id)}>
                    Sil
                  </button>{" "}
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Pagination
        itemsPerPage={10}
        totalItems={pagination?.totalElements || 0}
        onPageChange={pageChangeHandler}
        currentPage={pagination?.number || 0}
      />
    </div>
  );
};

export default StudentsPage;
