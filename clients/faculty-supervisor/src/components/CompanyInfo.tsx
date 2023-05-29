import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';

type ModalProps = {
  _company: number | null;
  isOpen: boolean;
  onClose: () => void;
  children?: React.ReactNode;
};

const CompanyInfo: React.FC<ModalProps> = ({ _company, isOpen, onClose, children }) => {
  const [company, setCompany] = useState<Company | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [id, setId] = useState<number>(1);

  useEffect(() => {
    fetch(`/api/company/${_company}`, {
      method: 'GET'
    })
      .then(response => response.json())
      .then(data => {
        console.log('data: ', data);
        const company = {
          ...data,
        };
        setCompany(company);
        setLoading(false);
        setId(id);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="bg-white p-5 rounded-md w-full pt-0">
      <div className="flex items-center justify-between pb-6">
        <div>
          <h1 className="font-bold text-4xl font-semibold mb-4">Şirket Bilgisi</h1>
        </div>
      </div>
      <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
        <table className="min-w-full leading-normal">
          <thead> </thead>
          <tbody>
            <tr>
              <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                Şirket Adı
              </th>
              <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                <p className="text-gray-900 whitespace-no-wrap">{company?.name}</p>
              </td>
            </tr>
            <tr>
              <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                Adres
              </th>
              <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                <p className="text-gray-900 whitespace-no-wrap">{company?.address}</p>
              </td>
            </tr>
            <tr>
              <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                Telefon Numarası
              </th>
              <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                <p className="text-gray-900 whitespace-no-wrap">{company?.phoneNumber}</p>
              </td>
            </tr>
            <tr>
              <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                Fax Numarası
              </th>
              <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                <p className="text-gray-900 whitespace-no-wrap">{company?.faxNumber}</p>
              </td>
            </tr>
            <tr>
              <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                E-Mail
              </th>
              <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                <p className="text-gray-900 whitespace-no-wrap">{company?.email}</p>
              </td>
            </tr>
            <tr>
              <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                Alan
              </th>
              <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                <p className="text-gray-900 whitespace-no-wrap">{company?.scope}</p>
              </td>
            </tr>
            <tr>
              <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                Açıklama
              </th>
              <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                <p className="text-gray-900 whitespace-no-wrap">{company?.description}</p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div >
  );
}

export default CompanyInfo;
