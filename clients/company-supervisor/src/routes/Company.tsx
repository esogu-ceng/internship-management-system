import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';

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
  const [updatedCompanyInfo, setUpdatedCompanyInfo] = useState<Company | any>(
    {}
  );
  const [editMode, setEditMode] = useState<boolean>(false);

  useEffect(() => {
    fetch(`/api/company/${_companyId}`, {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setCompany(data);
        setLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const updateCurrentCompany = async (data: Company) => {
    try {
      const response = await fetch(`/api/company/`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });
      if (response.ok) {
        console.log('Company updated successfully');
        setCompany(updatedCompanyInfo);
      } else {
        console.error('Failed to update company');
      }
    } catch (error) {
      console.error('An error occurred while updating company', error);
    }
  };

  const handleEditClick = () => {
    setUpdatedCompanyInfo(companyInfo);
    setEditMode(true);
  };

  const handleCancelClick = () => {
    setEditMode(false);
    setUpdatedCompanyInfo(companyInfo);
  };

  const handleSaveClick = async () => {
    updateCurrentCompany(updatedCompanyInfo);
    setEditMode(false);
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    dataField: keyof Company
  ) => {
    setUpdatedCompanyInfo((prev: Company | any) => ({
      ...prev,
      [dataField]: e.target.value,
    }));
  };

  return (
    <div
      className="flex flex-col items-center justify-between"
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
              <tr
                key={dataField}
                className={`text-gray-700 ${
                  editMode
                    ? 'border-2 border-[1px] border-indigo-300'
                    : 'border-22c55e border'
                }`}
              >
                <td
                  id="fieldName"
                  className="dark:border-dark-5 p-4"
                  style={{ fontWeight: 'bold', color: '#3A4F7A' }}
                >
                  {field}
                </td>
                <td className="dark:border-dark-5 p-4">
                  {editMode ? (
                    <input
                      id="textField"
                      type="text"
                      key={dataField}
                      value={updatedCompanyInfo?.[dataField]}
                      onChange={(e) =>
                        handleChange(e, dataField as keyof Company)
                      }
                      style={{
                        maxWidth: '800px',
                        minWidth: '800px',
                        wordWrap: 'break-word',
                      }}
                    />
                  ) : (
                    <div
                      id="textField"
                      style={{
                        maxWidth: '800px',
                        minWidth: '800px',
                        wordWrap: 'break-word',
                      }}
                    >
                      {companyInfo?.[dataField as keyof Company]}
                    </div>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="dark:border-dark-5 p-4">
        {editMode ? (
          <div className="flex items-center gap-4">
            <div className="flex items-center">
              <hr
                style={{ width: '300px' }}
                className="border-t-2 border-green-500"
              />
            </div>
            <button
              id="save"
              className="undefined rounded-full border border-green-500 px-4 py-2 text-base text-green-500 transition-colors hover:bg-green-50"
              onClick={handleSaveClick}
            >
              &#10004; Kaydet
            </button>
            <button
              id="cancel"
              className="undefined rounded-full border border-red-500 px-4 py-2 text-base text-red-500 transition-colors hover:bg-red-50"
              onClick={handleCancelClick}
            >
              &#10006; Geri Al
            </button>
            <div className="flex items-center">
              <hr
                style={{ width: '300px' }}
                className="border-t-2 border-red-500"
              />
            </div>
          </div>
        ) : (
          <div className="flex items-center gap-4">
            <div className="flex items-center">
              <hr
                style={{ width: '351px' }}
                className="border-t-2 border-indigo-500"
              />
            </div>
            <button
              id="edit"
              className="undefined rounded-full border border-indigo-500 px-4 py-2 text-base text-indigo-500 transition-colors hover:bg-indigo-50"
              onClick={handleEditClick}
            >
              &#9998; Düzenle
            </button>
            <div className="flex items-center">
              <hr
                style={{ width: '351px' }}
                className="border-t-2 border-indigo-500"
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
};
