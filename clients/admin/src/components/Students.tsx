import React, { useState } from "react";
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

  const [searchTerm, setSearchTerm] = useState("");

  const handleOpenModal = () => {
    setIsAddModalOpen(true);
    document.body.style.overflow = "hidden";
  };

  const handleDeleteUser = (id:number) => {
    // ask for confirmation before deleting
    if (!window.confirm("Silmek istediğinize emin misiniz?")) return;
    // delete the student
    deleteStudent(id);
  };

  const filteredStudents = students.filter((student) => {
    const searchString = `${student.name} ${student.surname} ${student.tckn} ${student.studentNo}`;
    return searchString.toLowerCase().includes(searchTerm.toLowerCase());
  });

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
          onClose={() => setIsUpdateModalOpen(false)}
          faculties={faculties}
        />
      )}
      <button className="add-button" onClick={handleOpenModal}>
        <span>+ Öğrenci Ekle</span>
      </button>

      <input
        className="search-input"
        type="text"
        placeholder="İsim, Soyisim, T.C. Kimlik No veya Öğrenci No ara"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />

      <table>
        <thead>
          <tr>
            {tableHeaders.map((header) => (
              <th key={header}>{header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {filteredStudents.map((student) => (
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
