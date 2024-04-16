import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { InternshipJournalsType } from '../types/InternshipJournalsType';
import { ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const InternshipJournals: React.FC<{ internship_id: number }> = ({ internship_id }) => {
  const [loading, setLoading] = useState(true);
  const [internshipJournals, setInternshipJournals] = useState<InternshipJournalsType[]>([]);
  const [journalName, setJournalName] = useState<string>('loading');


  useEffect(() => {
    const fetchInternshipJournals = async () => {
      try {
        const response = await axios.get(`/api/internshipjournals/internship/${internship_id}`);
        setInternshipJournals(response.data);
        setJournalName(response.data[0]?.journal || 'loading');
 
        setLoading(false);
        
      } catch (error) {
        console.error('Error fetching internship journals:', error);
        setLoading(false);
      }
    };
    
    fetchInternshipJournals();
  }, [internship_id]);

  const handleApproval = async (index: number) => {
    try {
      // Assuming there is an endpoint to approve a single journal entry
      const response = await axios.put(`/api/internshipjournals/confirm/${internshipJournals[index].id}`);
      
      if (response.status === 200) {
        console.log('Journal at index', index, 'approved.');
        const updatedJournals = [...internshipJournals];
        updatedJournals[index].confirmation = true;
        setInternshipJournals(updatedJournals);
        toast.success('Günlük Onaylandı.', {
            position: 'top-right',
            autoClose: 3000, // Close the notification after 3 seconds
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
      } else {
        console.error('Failed to approve journal at index', index);
      }
    } catch (error) {
      console.error('Error approving journal:', error);
    }
  };
  
  const handleApproveAll = async () => {
    try {
        
      
      const response = await axios.put(`/api/internshipjournals/confirm-all/${internship_id}`);
      
      if (response.status === 200) {
        console.log('All journals approved.');
        
        const updatedJournals = internshipJournals.map(journal => ({ ...journal, confirmation: true }));
        setInternshipJournals(updatedJournals);
        toast.success('Tüm Günlükler Onaylandı.', {
          position: 'top-right',
          autoClose: 3000, 
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        
      } else {
        console.error('Failed to approve all journals.');
      }
    } catch (error) {
      console.error('Error approving all journals:', error);
    }
  };
  

  return (
    
    <div className="w-full flex flex-col justify-center items-end">
    <div className="container mx-auto px-4 sm:px-8">
      <div className="py-8">
        <div className="-mx-4 overflow-x-auto px-4 py-4 sm:-mx-8 sm:px-8">
          <div className="inline-block min-w-full overflow-hidden rounded-lg shadow">
            <div className="table-container" style={{ maxHeight: '600px', overflowY: 'auto' }}>
              <table className="min-w-full leading-normal">
                  <thead>
                    <tr>
                    <th
                      scope="col"
                      className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                    >
                      Journal
                    </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Operation Time
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Starting Date
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        End Date
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Unit Name
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Confirmation
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Action
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {internshipJournals.map((journal, index) => (
                      <tr key={index}>
                        <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                          <div className="flex items-center">
                            <div className="ml-3">
                              <p className="whitespace-wrap break-words text-gray-900">
                                {journal.journal}
                              </p>
                            </div>
                          </div>
                        </td>
                        <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                          <p className="whitespace-no-wrap text-gray-900">
                            {journal.operationTime}
                          </p>
                        </td>
                        <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                          <p className="whitespace-no-wrap text-gray-900">
                            {journal.startingDate}
                          </p>
                        </td>
                        <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                          <p className="whitespace-no-wrap text-gray-900">
                            {journal.endDate}
                          </p>
                        </td>
                        <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                          <p className="whitespace-no-wrap text-gray-900">
                            {journal.unitName}
                          </p>
                        </td>
                        <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                          <p className="whitespace-no-wrap text-gray-900">
                            {journal.confirmation ? 'Onaylı' : ' Onaysız'}
                          </p>
                        </td>
                        <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                          {!journal.confirmation && (
                            <button
                              onClick={() => handleApproval(index)}
                              
                              className="w-full rounded-lg bg-indigo-600 px-4 py-2 text-center text-base font-semibold text-white shadow-md transition duration-200 ease-in hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 focus:ring-offset-indigo-200"
                            >
                                
                              Onayla
                            </button>
                            
                          )}
                          
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
              <div className="flex justify-end mt-4 md:mt-10 md:ml-4">
                <button
                  onClick={handleApproveAll}
                  className="w-auto rounded-lg bg-green-600 px-4 py-2 text-center text-base font-semibold text-white shadow-md transition duration-200 ease-in hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 focus:ring-offset-green-200"
                >
                  Hepsi Onayla
                </button>
                
              </div>
            </div>
          </div>
        </div>
      </div>
      <ToastContainer />
    </div>
  );
  
};

export default InternshipJournals;
