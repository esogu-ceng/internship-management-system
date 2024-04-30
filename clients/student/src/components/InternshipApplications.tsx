import React, { useEffect, useState } from 'react';

const InternshipApplication = () => {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCompanyId, setSelectedCompanyId] = useState<number | null>(null);
  const [showConfirmationModal, setShowConfirmationModal] = useState<boolean>(false);
  const [userAgreement, setUserAgreement] = useState<string>('');

  useEffect(() => {
    // Assuming this endpoint returns all companies
    const fetchData = async () => {
      try {
        const companiesResponse = await fetch('/api/company/getAllCompanies');
        const companiesData = await companiesResponse.json();
        setCompanies(companiesData.content);

        const agreementResponse = await fetch('/api/setting/user_agreement');
        const agreementData = await agreementResponse.json();
        setUserAgreement(agreementData.value);

        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const handleApply = (companyId: number) => {
    setSelectedCompanyId(companyId);
    setShowConfirmationModal(true);
  };

  const handleConfirmation = (confirm: boolean) => {
    if (confirm && selectedCompanyId !== null) {
      // Implement your apply logic here, such as opening a modal or navigating to apply page
      console.log('Applying for company ID:', selectedCompanyId);
    }
    setShowConfirmationModal(false);
    setSelectedCompanyId(null);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>List of Companies</h1>
      <div className="grid grid-cols-3 gap-4">
        {companies.map((company: { id: number; name: string; scope: string; description: string }) => (
          <div key={company.id} className="bg-white rounded-lg p-6 shadow-md">
            <h2 className="text-xl font-bold">{company.name}</h2>
            <p className="text-gray-600">{company.scope}</p>
            <p className="mt-4">{company.description}</p>
            <button
              className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
              onClick={() => handleApply(company.id)}
            >
              Apply
            </button>
          </div>
        ))}
      </div>

      {/* Confirmation Modal */}
      {showConfirmationModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-lg shadow-md">
            <p>{userAgreement}</p>
            <div className="flex justify-between mt-4">
              <button
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mr-2"
                onClick={() => handleConfirmation(true)}
              >
                Yes
              </button>
              <button
                className="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded"
                onClick={() => handleConfirmation(false)}
              >
                No
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default InternshipApplication;
