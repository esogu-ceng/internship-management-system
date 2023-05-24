import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';
import { CompanyRow } from '../components/CompanyRow';

export const CompanyPage = ({_companyId, _auth}:{_companyId: number, _auth: string}) => {
  const [loading, setLoading] = useState(true);
  const [companyInfo, setCompany] = useState<Company | undefined>();
  const [editMode, setEditMode] = useState(false);

  useEffect(() => {
    fetch(`/api/company/${_companyId}`, {
      headers: {
        Authorization:
          'Basic ' + btoa(_auth), 
      },
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setLoading(false);
        setCompany(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const updateCurrentCompany = async (data: any) => {
    const credentials = btoa(_auth);
    console.log(data);
    try {
      const response = await fetch(`/api/company/`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify(data)
      });
      if (response.ok) {
        console.log('Company updated successfully');
      } else {
        console.error('Failed to update company');
      }
    } catch (error) {
      console.error('An error occurred while updating company', error);
    }
  };

  const handleEditClick = () => {
    setEditMode(true);
  };

  const handleSaveClick = (dataField: keyof Company, editedValue: string) => {
    const updatedCompanyInfo = { ...companyInfo, [dataField]: editedValue };
    updateCurrentCompany(updatedCompanyInfo); 
    setEditMode(false);
  };

  return (
    <div className="flex flex-col justify-between items-center">
      {/* <Header/> */}
      <div>
        <table className="text-gray-700">
          <tbody>
            <CompanyRow companyId={companyInfo?.id} field='İSİM' dataField='name' value={companyInfo?.name} onEditClick={handleEditClick} onSaveClick={handleSaveClick} />
            <CompanyRow companyId={companyInfo?.id} field='ALAN' dataField='scope' value={companyInfo?.scope} onEditClick={handleEditClick} onSaveClick={handleSaveClick} />
            <CompanyRow companyId={companyInfo?.id} field='TELEFON NUMARASI' dataField='phoneNumber' value={companyInfo?.phoneNumber} onEditClick={handleEditClick} onSaveClick={handleSaveClick} />
            <CompanyRow companyId={companyInfo?.id} field='FAX NUMARASI' dataField='faxNumber' value={companyInfo?.faxNumber} onEditClick={handleEditClick} onSaveClick={handleSaveClick} />
            <CompanyRow companyId={companyInfo?.id} field='EMAIL' dataField='email' value={companyInfo?.email} onEditClick={handleEditClick} onSaveClick={handleSaveClick} />
            <CompanyRow companyId={companyInfo?.id} field='AÇIKLAMA' dataField='description' value={companyInfo?.description} onEditClick={handleEditClick} onSaveClick={handleSaveClick} />
            <CompanyRow companyId={companyInfo?.id} field='ADRES' dataField='address' value={companyInfo?.address} onEditClick={handleEditClick} onSaveClick={handleSaveClick} />
          </tbody>
        </table>
      </div>
    </div>
  );
};
