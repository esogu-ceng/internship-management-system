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
    supervisorNumber: string;
}

type Action =
  | { type: "UPDATE_NAME"; value: string }
  | { type: "UPDATE_SURNAME"; value: string }
  | { type: "UPDATE_PHONE_NUMBER"; value: string }
  | { type: "UPDATE_SUPERVISOR_NUMBER"; value: string };

const initialState: State = {
    name: "",
    surname: "",
    phoneNumber: "",
    supervisorNumber : "",
};

const reducer = (state: State, action: Action): State => {
    switch (action.type) {
      case "UPDATE_NAME":
        return { ...state, name: action.value };
      case "UPDATE_SURNAME":
        return { ...state, surname: action.value };
      case "UPDATE_PHONE_NUMBER":
        return { ...state, phoneNumber: action.value };
      case "UPDATE_SUPERVISOR_NUMBER":
        return { ...state, supervisorNumber: action.value };
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
        type: "UPDATE_PHONE_NUMBER",
        value: facultySupervisorDto.phoneNumber,
      });
      dispatch({
        type: "UPDATE_SUPERVISOR_NUMBER",
        value: facultySupervisorDto.supervisorNumber,
      });
    }, [facultySupervisorDto]);
  
    const handleSubmit = (event: React.FormEvent) => {
      event.preventDefault();
  
      const updatedFacultySupervisorDto: FacultySuperviserUpdate = {
        id: facultySupervisorDto.id,
        name: state.name,
        surname: state.surname,
        phoneNumber: state.phoneNumber,
        supervisorNumber: state.supervisorNumber,
        user: {
          id: facultySupervisorDto.user.id,
        },
        faculty: {
          id: facultySupervisorDto.faculty.id,
        },
      };
  
      onUpdateFacultySupervisor(updatedFacultySupervisorDto);
      onClose();
    };
  
    return (
      <div className="update-modal">
        <div className="update-modal-content">
          <h2>Fakülte Sorumlusu Güncelle</h2>
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
              />
            </div>
            <div>
              <label htmlFor="surname">Soyadı:</label>
              <input
                type="text"
                id="surname"
                name="surname"
                value={state.surname}
                onChange={(e) =>
                  dispatch({ type: "UPDATE_SURNAME", value: e.target.value })
                }
                required
              />
            </div>
            <div>
              <label htmlFor="phoneNumber">Telefon Numarası:</label>
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
              />
            </div>
            <div className="update-modal-buttons">
              <button type="submit">Update</button>
              <button type="button" onClick={onClose}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  };
  
export default UpdateModalForm;
  