/** @format */

import React, { useReducer, useEffect } from "react";
import {
  CompanySupervisor,
  CompanySuperviserUpdate,
} from "../types/CompanySupervisors";

interface UpdateModalFormProps {
  companySupervisorDto: CompanySupervisor;
  onUpdateCompanySupervisor: (
    updatedCompanySupervisorDto: CompanySuperviserUpdate
  ) => void;
  onClose: () => void;
}

interface State {
  name: string;
  surname: string;
  phoneNumber: string;
}

type Action =
  | { type: "UPDATE_NAME"; value: string }
  | { type: "UPDATE_SURNAME"; value: string }
  | { type: "UPDATE_PHONE_NUMBER"; value: string };

const initialState: State = {
  name: "",
  surname: "",
  phoneNumber: "",
};

const reducer = (state: State, action: Action): State => {
  switch (action.type) {
    case "UPDATE_NAME":
      return { ...state, name: action.value };
    case "UPDATE_SURNAME":
      return { ...state, surname: action.value };
    case "UPDATE_PHONE_NUMBER":
      return { ...state, phoneNumber: action.value };
    default:
      return state;
  }
};

const UpdateModalForm: React.FC<UpdateModalFormProps> = ({
  companySupervisorDto,
  onUpdateCompanySupervisor,
  onClose,
}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    dispatch({ type: "UPDATE_NAME", value: companySupervisorDto.name });
    dispatch({ type: "UPDATE_SURNAME", value: companySupervisorDto.surname });
    dispatch({
      type: "UPDATE_PHONE_NUMBER",
      value: companySupervisorDto.phoneNumber,
    });
  }, [companySupervisorDto]);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    const updatedCompanySupervisorDto: CompanySuperviserUpdate = {
      id: companySupervisorDto.id,
      name: state.name,
      surname: state.surname,
      phoneNumber: state.phoneNumber,
      user: {
        id: companySupervisorDto.user.id,
      },
      company: {
        id: companySupervisorDto.company.id,
      },
    };

    onUpdateCompanySupervisor(updatedCompanySupervisorDto);
    onClose();
  };

  return (
    <div className="update-modal">
      <div className="update-modal-content">
        <h2>Update Company Supervisor</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="name">Name:</label>
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
            <label htmlFor="surname">Surname:</label>
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
            <label htmlFor="phoneNumber">Phone Number:</label>
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
