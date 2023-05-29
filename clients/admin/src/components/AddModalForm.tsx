/** @format */

import React, { useReducer } from "react";
import { CompanySupervisorCreate } from "../types/CompanySupervisors";

interface State {
  name: string;
  surname: string;
  phoneNumber: string;
  user: {
    username: string;
    password: string;
    email: string;
  };
}

type Action =
  | { type: "UPDATE_FIELD"; field: string; value: string }
  | { type: "UPDATE_USER_FIELD"; field: string; value: string }
  | { type: "RESET_FORM" };

const initialState: State = {
  name: "",
  surname: "",
  phoneNumber: "",
  user: {
    username: "",
    password: "",
    email: "",
  },
};

interface Props {
  showModal: boolean;
  onShowModal: (showModal: boolean) => void;
  onAddCompanySupervisors: (companySupervisor: CompanySupervisorCreate) => void;
}

const reducer = (state: State, action: Action): State => {
  switch (action.type) {
    case "UPDATE_FIELD":
      return {
        ...state,
        [action.field]: action.value,
      };
    case "UPDATE_USER_FIELD":
      return {
        ...state,
        user: {
          ...state.user,
          [action.field]: action.value,
        },
      };
    case "RESET_FORM":
      return initialState;
    default:
      return state;
  }
};

const AddModalForm: React.FC<Props> = ({
  showModal,
  onShowModal,
  onAddCompanySupervisors,
}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    if (name.startsWith("user.")) {
      const field = name.split(".")[1];
      dispatch({ type: "UPDATE_USER_FIELD", field, value });
    } else {
      dispatch({ type: "UPDATE_FIELD", field: name, value });
    }
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    dispatch({ type: "RESET_FORM" });
    onAddCompanySupervisors(state);
    onShowModal(false);
  };

  return (
    <div>
      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h2 className="modal-title">Modal Form</h2>
            <form onSubmit={handleSubmit} className="modal-form">
              <div className="form-group">
                <label htmlFor="name" className="form-label">
                  Ad:
                </label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  value={state.name}
                  onChange={handleChange}
                  className="form-input"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="surname" className="form-label">
                  Soyad:
                </label>
                <input
                  type="text"
                  id="surname"
                  name="surname"
                  value={state.surname}
                  onChange={handleChange}
                  className="form-input"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="phoneNumber" className="form-label">
                  Telefon Numarası:
                </label>
                <input
                  type="text"
                  id="phoneNumber"
                  name="phoneNumber"
                  value={state.phoneNumber}
                  onChange={handleChange}
                  className="form-input"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="username" className="form-label">
                  Kullanıcı Adı:
                </label>
                <input
                  type="text"
                  id="username"
                  name="user.username"
                  value={state.user.username}
                  onChange={handleChange}
                  className="form-input"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="password" className="form-label">
                  Şifre:
                </label>
                <input
                  type="password"
                  id="password"
                  name="user.password"
                  value={state.user.password}
                  onChange={handleChange}
                  className="form-input"
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="email" className="form-label">
                  Email:
                </label>
                <input
                  type="email"
                  id="email"
                  name="user.email"
                  value={state.user.email}
                  onChange={handleChange}
                  className="form-input"
                  required
                />
              </div>

              <div className="form-buttons">
                <button type="submit" className="submit-button">
                  Kaydet
                </button>
                <button
                  type="button"
                  className="cancel-button"
                  onClick={() => onShowModal(false)}>
                  İptal
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default AddModalForm;
