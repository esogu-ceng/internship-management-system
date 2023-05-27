import React from 'react';
import './ErrorModal.css';
import { ErrorModalProps } from '../../types/Error';


const ErrorModal: React.FC<ErrorModalProps> = ({ message, onClose }) => {
  return (
    <div className="error-modal">
      <div className="error-modal-content">
        <p>{message}</p>
        <button onClick={onClose}>OK</button>
      </div>
    </div>
  );
};

export default ErrorModal;
