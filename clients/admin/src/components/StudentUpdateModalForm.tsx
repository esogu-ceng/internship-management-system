/** @format */

import React, { useReducer, useEffect } from "react";
import {
  Faculty,
  Student,
  StudentUpdate,
  UserUpdate,
} from "../types/StudentType";
import { toast } from "react-toastify";

interface UpdateModalFormProps {
  studentDto: Student;
  onUpdateStudent: (updatedStudentDto: StudentUpdate) => void;
  faculties: Faculty[];
  onClose: () => void;
}

interface State {
  id: number;
  name: string;
  surname: string;
  tckn: string;
  studentNo: string;
  grade: string;
  phoneNumber: string;
  birthPlace: string;
  birthDate: Date;
  user: UserUpdate;
  faculty: Faculty;
  address: string;
}

type Action =
  | { type: "UPDATE_ID"; value: number }
  | { type: "UPDATE_NAME"; value: string }
  | { type: "UPDATE_SURNAME"; value: string }
  | { type: "UPDATE_TCKN"; value: string }
  | { type: "UPDATE_STUDENT_NO"; value: string }
  | { type: "UPDATE_GRADE"; value: string }
  | { type: "UPDATE_PHONE_NUMBER"; value: string }
  | { type: "UPDATE_BIRTH_PLACE"; value: string }
  | { type: "UPDATE_BIRTH_DATE"; value: Date }
  | { type: "UPDATE_ADDRESS"; value: string }
  | { type: "UPDATE_FACULTY_ID"; value: number }
  | { type: "UPDATE_USER_EMAIL"; value: string };

const initialState: State = {
  id: 0,
  name: "",
  surname: "",
  tckn: "",
  studentNo: "",
  grade: "",
  phoneNumber: "",
  birthPlace: "",
  birthDate: new Date(""),
  user: {
    id: 0,
    email: "",
    activity: true,
  },
  faculty: {
    id: 0,
    name: "",
  },
  address: "",
};

const reducer = (state: State, action: Action): State => {
  switch (action.type) {
    case "UPDATE_ID":
      return { ...state, id: action.value };
    case "UPDATE_NAME":
      return { ...state, name: action.value };
    case "UPDATE_SURNAME":
      return { ...state, surname: action.value };
    case "UPDATE_TCKN":
      return { ...state, tckn: action.value };
    case "UPDATE_STUDENT_NO":
      return { ...state, studentNo: action.value };
    case "UPDATE_GRADE":
      return { ...state, grade: action.value };
    case "UPDATE_PHONE_NUMBER":
      return { ...state, phoneNumber: action.value };
    case "UPDATE_BIRTH_PLACE":
      return { ...state, birthPlace: action.value };
    case "UPDATE_BIRTH_DATE":
      return { ...state, birthDate: action.value };
    case "UPDATE_ADDRESS":
      return { ...state, address: action.value };
    case "UPDATE_FACULTY_ID":
      return { ...state, faculty: { ...state.faculty, id: action.value } };
    case "UPDATE_USER_EMAIL":
      return { ...state, user: { ...state.user, email: action.value } };
    default:
      return state;
  }
};

const UpdateModalForm: React.FC<UpdateModalFormProps> = ({
  studentDto,
  onUpdateStudent,
  onClose,
  faculties,
}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    dispatch({ type: "UPDATE_ID", value: studentDto.id });
    dispatch({ type: "UPDATE_NAME", value: studentDto.name });
    dispatch({ type: "UPDATE_SURNAME", value: studentDto.surname });
    dispatch({ type: "UPDATE_TCKN", value: studentDto.tckn });
    dispatch({ type: "UPDATE_STUDENT_NO", value: studentDto.studentNo });
    dispatch({ type: "UPDATE_GRADE", value: studentDto.grade });
    dispatch({ type: "UPDATE_PHONE_NUMBER", value: studentDto.phoneNumber });
    dispatch({ type: "UPDATE_BIRTH_PLACE", value: studentDto.birthPlace });
    dispatch({ type: "UPDATE_BIRTH_DATE", value: studentDto.birthDate });
    dispatch({ type: "UPDATE_ADDRESS", value: studentDto.address });
    dispatch({ type: "UPDATE_FACULTY_ID", value: studentDto.faculty.id });
    dispatch({ type: "UPDATE_USER_EMAIL", value: studentDto.user.email });
  }, [studentDto]);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    const updatedStudentDto: StudentUpdate = {
      id: studentDto.id,
      name: state.name,
      surname: state.surname,
      tckn: state.tckn,
      studentNo: state.studentNo,
      grade: state.grade,
      phoneNumber: state.phoneNumber,
      birthPlace: state.birthPlace,
      birthDate: new Date(state.birthDate),
      user: {
        id: studentDto.user.id,
        email: state.user.email,
        activity: studentDto.user.activity,
      },
      facultyId: state.faculty.id,
      address: state.address,
    };

    // validate grade
    if (
      !/^\d+(\.\d+)?$/.test(state.grade) ||
      parseFloat(state.grade) < 0 ||
      parseFloat(state.grade) > 100
    ) {
      toast.error("Not 0 ile 4 veya 0 ile 100 arasında bir sayı olmalıdır! Örn: 2.00 veya 50");
      return;
    }

    onUpdateStudent(updatedStudentDto);
    onClose();
  };

  return (
    <div className="update-modal">
      <div className="update-modal-content">
        <div className="modal-header">
          <h2 className="modal-title">Öğrenci Ekle</h2>
          <div className="modal-close-icon" onClick={onClose}>
            X
          </div>
        </div>
        <div style={{ maxHeight: "80vh", overflowY: "auto" }}>
          <div style={{ width: "100%", overflowX: "auto" }}></div>
          <form onSubmit={handleSubmit} className="modal-form">
            <div className="form-group">
              <label htmlFor="id" className="form-label">
                Id:
              </label>
              <input
                type="number"
                readOnly={true}
                id="id"
                name="id"
                className="form-input"
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_ID",
                    value: parseInt(e.target.value),
                  })
                }
                defaultValue={studentDto.id}
                disabled={true}
              />
            </div>
            <div>
              <label htmlFor="name" className="form-label">
                Ad:
              </label>
              <input
                type="text"
                id="name"
                name="name"
                value={state.name}
                onChange={(e) =>
                  dispatch({ type: "UPDATE_NAME", value: e.target.value })
                }
                className="form-input"
                required
                maxLength={80}
                placeholder={"Ad"}
              />
            </div>
            <div>
              <label htmlFor="surname" className="form-label">
                Soyad:
              </label>
              <input
                type="text"
                id="surname"
                name="surname"
                value={state.surname}
                onChange={(e) =>
                  dispatch({ type: "UPDATE_SURNAME", value: e.target.value })
                }
                className="form-input"
                required
                maxLength={80}
                placeholder={"Soyad"}
              />
            </div>
            <div>
              <label htmlFor="tckn" className="form-label">
                T.C. Kimlik No:
              </label>
              <input
                type="text"
                id="tckn"
                name="tckn"
                value={state.tckn}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_TCKN",
                    value: e.target.value,
                  })
                }
                className="form-input"
                required
                minLength={11}
                maxLength={11}
                placeholder={"11122233344"}
              />
            </div>
            <div>
              <label htmlFor="studentNo" className="form-label">
                Öğrenci No:
              </label>
              <input
                type="text"
                id="studentNo"
                name="studentNo"
                value={state.studentNo}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_STUDENT_NO",
                    value: e.target.value,
                  })
                }
                className="form-input"
                required
                minLength={8}
                maxLength={12}
                placeholder={"152120201000"}
              />
            </div>
            <div>
              <label htmlFor="grade" className="form-label">
                Not Ortalaması:
              </label>
              <input
                type="text"
                id="grade"
                name="grade"
                value={state.grade}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_GRADE",
                    value: e.target.value,
                  })
                }
                className="form-input"
                required
                placeholder={"2.00 veya 50.750"}
                maxLength={6} // 2.575/4.00 or 90.750/100
              />
            </div>
            <div>
              <label htmlFor="phoneNumber" className="form-label">
                Cep Telefonu:
              </label>
              <input
                type="text"
                id="phoneNumber"
                name="phoneNumber"
                value={state.phoneNumber}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_PHONE_NUMBER",
                    value: e.target.value,
                  })
                }
                className="form-input"
                required
                minLength={10} // 555 444 33 22
                maxLength={10} // in the database it is 10
                placeholder={"5554443322"}
                pattern="[0-9]{10}"
                title="Telefon numarası 10 haneli olmalıdır!"
              />
            </div>
            <div>
              <label htmlFor="birthPlace" className="form-label">
                Doğum Yeri:
              </label>
              <input
                type="text"
                id="birthPlace"
                name="birthPlace"
                value={state.birthPlace}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_BIRTH_PLACE",
                    value: e.target.value,
                  })
                }
                className="form-input"
                required
                placeholder={"Eskişehir"}
              />
            </div>
            <div>
              <label htmlFor="birthDate" className="form-label">
                Doğum Tarihi:
              </label>
              <input
                type="date"
                id="birthDate"
                name="birthDate"
                value={new Date(state.birthDate)
                  .toLocaleDateString("en-GB")
                  .split("/")
                  .reverse()
                  .join("-")}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_BIRTH_DATE",
                    value: new Date(e.target.value),
                  })
                }
                className="form-input"
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="facultyId" className="form-label">
                Fakülte:
              </label>
              <select
                id="facultyId"
                name="facultyId"
                value={state.faculty.id}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_FACULTY_ID",
                    value: parseInt(e.target.value),
                  })
                }
                className="form-input"
                required
              >
                <option value="">Seçiniz</option>
                {faculties.map((faculty) => (
                  <option key={faculty.id} value={faculty.id}>
                    {faculty.name}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label htmlFor="address" className="form-label">
                Adres:
              </label>
              <input
                type="text"
                id="address"
                name="address"
                value={state.address}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_ADDRESS",
                    value: e.target.value,
                  })
                }
                className="form-input"
                required
                placeholder={"e.g Odunpazarı/Eskişehir"}
              />
            </div>
            <div>
              <label htmlFor="user.email" className="form-label">
                Email:
              </label>
              <input
                type="text"
                id="user.email"
                name="user.email"
                value={state.user.email}
                onChange={(e) =>
                  dispatch({
                    type: "UPDATE_USER_EMAIL",
                    value: e.target.value,
                  })
                }
                required
                className="form-input"
                placeholder={"Email"}
              />
            </div>
            <div className="update-modal-buttons">
              <button type="submit" className="submit-button">
                Kaydet
              </button>
              <button
                type="button"
                className="cancel-button-1"
                onClick={onClose}
              >
                İptal
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default UpdateModalForm;
