import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';
import { CompanyRow } from '../components/CompanyRow';

const companyRows = [
  { field: 'İSİM', dataField: 'name' },
  { field: 'ALAN', dataField: 'scope' },
  { field: 'TELEFON NUMARASI', dataField: 'phoneNumber' },
  { field: 'FAX NUMARASI', dataField: 'faxNumber' },
  { field: 'EMAIL', dataField: 'email' },
  { field: 'AÇIKLAMA', dataField: 'description' },
  { field: 'ADRES', dataField: 'address' },
];

export const CompanyPage = ({ _companyId }: { _companyId: number }) => {
  const [loading, setLoading] = useState<boolean>(true);
  const [companyInfo, setCompany] = useState<Company | undefined>();
  const [editMode, setEditMode] = useState<boolean>(false);

  useEffect(() => {
    fetch(`/api/company/${_companyId}`, {
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
    console.log(data);
    try {
      const response = await fetch(`/api/company/`, {
        method: 'PUT',
        body: JSON.stringify(data),
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

  const handleSaveClick = (
    dataField: keyof Company,
    editedValue: string | number | undefined
  ) => {
    const updatedCompanyInfo = { ...companyInfo, [dataField]: editedValue };
    updateCurrentCompany(updatedCompanyInfo);
    setEditMode(false);
  };

  return (
    <div
      className="flex flex-col justify-between items-center"
      style={{ marginTop: '100px' }}
    >
      <div>
        <h1
          style={{
            textAlign: 'center',
            fontWeight: 'bold',
            fontSize: '20px',
            color: '#3A4F7A',
          }}
        >
          ŞİRKET BİLGİLERİ
        </h1>
        <br />
        <table className="text-gray-700">
          <tbody>
            {companyRows.map(({ field, dataField }) => (
              <CompanyRow
                key={dataField}
                companyId={companyInfo?.id}
                field={field}
                dataField={dataField as keyof Company}
                value={companyInfo?.[dataField as keyof Company]}
                onEditClick={handleEditClick}
                onSaveClick={handleSaveClick}
              />
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
