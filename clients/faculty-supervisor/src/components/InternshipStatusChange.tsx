import React, { useState, ChangeEvent, FormEvent } from 'react';
import axios from 'axios';
import ErrorModal from '../modals/ErrorModal/ErrorModal';
import './InternshipStatusChange.css'
interface InternshipStatusChangeProps {
  id: number;
  onStatusChange: () => void;
}

const InternshipStatusChange: React.FC<InternshipStatusChangeProps> = ({ id, onStatusChange }) => {
  const [newStatus, setNewStatus] = useState('');
  const [error, setError] = useState('');
  const [showModal, setShowModal] = useState(false);

  const handleStatusChange = (e: ChangeEvent<HTMLSelectElement>) => {
    setNewStatus(e.target.value);
    setError('');
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (newStatus === '') {
      setError('Please select a status');
      setShowModal(true);
      return;
    }

    try {
      // Perform the HTTP PUT request to update the internship status
      const response = await axios.put(`/api/internship/${newStatus}/${id}`, {});

      // Handle the response, update UI, etc.
     

      // Reset the form
      setNewStatus('');
      onStatusChange();
    } catch (error) {
      // Handle any errors that occur during the request
      
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    onStatusChange();
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <select
          value={newStatus}
          onChange={handleStatusChange}
          className="relative inline-block px-3 py-1 font-semibold leading-tight selectst "
        >
          <option value="">Select Status</option>
          <option value="approve">Approve</option>
          <option value="pending">Pending</option>
          <option value="reject">Rejected</option>
        </select>
        <button
          type="submit"
          className="relative inline-block px-3 py-1 font-semibold leading-tight btnstatus  "
        >
          <span className="relative " >Change Status</span>
        </button> 
      </form>
      {showModal && <ErrorModal message={error} onClose={handleCloseModal} />}
    </div>
  );
};

export default InternshipStatusChange;
