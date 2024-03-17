/** @format */

import React, { useReducer, useEffect } from "react";
import {
  FacultySupervisor,
  FacultySuperviserUpdate,
} from "../types/FacultySuperviosr";

interface UpdateModalFormProps {
  facultySupervisorDto: FacultySupervisor;
  onUpdateFacultySupervisor: (
    updatedFacultySupervisorDto: FacultySuperviserUpdate
  ) => void;
  onClose: () => void;
}

interface State {
  name: string;
  surname: string;
  phoneNumber: string;
  supervisorNo: string;
  activity: boolean;
}

type Action =
  | { type: "UPDATE_NAME"; value: string }
  | { type: "UPDATE_SURNAME"; value: string }
  | { type: "UPDATE_SUPERVISORNO"; value: string }
  | { type: "UPDATE_ACTIVITY"; value: boolean }
  | { type: "UPDATE_PHONE_NUMBER"; value: string };

const initialState: State = {
  name: "",
  surname: "",
  phoneNumber: "",
  supervisorNo: "",
  activity: false,
};

const reducer = (state: State, action: Action): State => {
  switch (action.type) {
    case "UPDATE_NAME":
      return { ...state, name: action.value };
    case "UPDATE_SURNAME":
      return { ...state, surname: action.value };
    case "UPDATE_SUPERVISORNO":
      return { ...state, supervisorNo: action.value };
    case "UPDATE_PHONE_NUMBER":
      return { ...state, phoneNumber: action.value };
    case "UPDATE_ACTIVITY":
      return { ...state, activity: action.value };
    default:
      return state;
  }
};

const UpdateModalForm: React.FC<UpdateModalFormProps> = ({
  facultySupervisorDto,
  onUpdateFacultySupervisor,
  onClose,
}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    dispatch({ type: "UPDATE_NAME", value: facultySupervisorDto.name });
    dispatch({ type: "UPDATE_SURNAME", value: facultySupervisorDto.surname });
    dispatch({
      type: "UPDATE_SUPERVISORNO",
      value: facultySupervisorDto.supervisorNo,
    });
    dispatch({
      type: "UPDATE_PHONE_NUMBER",
      value: facultySupervisorDto.phoneNumber,
    });
    dispatch({
      type: "UPDATE_ACTIVITY",
      value: facultySupervisorDto.user.activity,
    });
  }, [facultySupervisorDto]);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    const updatedFacultySupervisorDto: FacultySuperviserUpdate = {
      id: facultySupervisorDto.id,
      name: state.name,
      surname: state.surname,
      phoneNumber: state.phoneNumber,
      supervisorNo: state.supervisorNo,
      faculty: {
        id: facultySupervisorDto.facultyId,
      },
      user: {
        id: facultySupervisorDto.user.id,
        activity: state.activity,
      },
    };

    onUpdateFacultySupervisor(updatedFacultySupervisorDto);
    onClose();
  };

  return (
    <div className="update-modal">
      <div className="update-modal-content">
        <h2 className="modal-title">Fakülte Sorumlusu Güncelle</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="name">Ad:</label>
            <input
              type="text"
              id="name"
              name="name"
              value={state.name}
              onChange={(e) =>
                dispatch({ type: "UPDATE_NAME", value: e.target.value })
              }
              required
              placeholder="Ad"
              maxLength={80}
            />
          </div>
          <div>
            <label htmlFor="surname">Soyad:</label>
            <input
              type="text"
              id="surname"
              name="surname"
              value={state.surname}
              onChange={(e) =>
                dispatch({ type: "UPDATE_SURNAME", value: e.target.value })
              }
              required
              placeholder="Soyad"
              maxLength={80}
            />
          </div>
          <div>
            <label htmlFor="supervisorNo">Fakülte Sorumlusu Id:</label>
            <input
              type="number"
              id="supervisorNo"
              name="supervisorNo"
              value={state.supervisorNo}
              onChange={(e) =>
                dispatch({ type: "UPDATE_SUPERVISORNO", value: e.target.value })
              }
              required
              min={0}
              placeholder="örn: 26"
            />
          </div>
          <div>
            <label htmlFor="phoneNumber">Telefon No:</label>
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
              required
              pattern="[0-9]{10}"
              title="Telefon numarası 10 haneli olmalıdır. Örnek: 5554443322"
              minLength={10} // 555 444 33 22
              maxLength={10} // in the database it is 10
              placeholder={"5554443322"}
            />
          </div>
          <div className="userActivity">
            <label htmlFor="activity">Aktiflik Durumu:</label>
            <input
              type="checkbox"
              id="activity"
              name="activity"
              checked={state.activity}
              onChange={(e) =>
                dispatch({ type: "UPDATE_ACTIVITY", value: e.target.checked })
              }
            />
          </div>
          <div className="update-modal-buttons">
            <button type="submit">Kaydet</button>
            <button type="button" className="cancel-button-1" onClick={onClose}>
              İptal
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateModalForm;
