import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';

function Companies() {

    const [companies, setCompanies] = useState<Company[] | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('/api/company/getAll', {
            headers: {
                Authorization: 'Basic ' + btoa('yagizharman4biz@gmail.com:password123')
            },
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                console.log('data: ', data.content);
                const companiesData = data.content.map((company: Company) => ({
                    ...company,
                    startDate: new Date(company.startDate),
                    endDate: new Date(company.endDate)
                }));
                setCompanies(companiesData);
                setLoading(false);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);
    return (
        <div className="bg-white p-5 rounded-md w-full pt-0">
            <div className="flex items-center justify-between pb-6">
                <div className = "header-firmalar"> 
                    <h1 className="font-bold text-4xl font-semibold mt-5">Firmalar</h1>
                </div>
            </div>
            <div className="flex items-center justify-between">
                <div className="flex bg-gray-50 items-center p-2 rounded-md">
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-5 w-5 text-gray-400"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                    >
                        <path
                            fillRule="evenodd"
                            d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                            clipRule="evenodd"
                        />
                    </svg>
                    <input className="bg-gray-50 outline-none ml-1 block" type="text" placeholder="Search..." />{/* TODO */}
                </div>
            </div>
            <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                <div className="inline-block min-w-full shadow rounded-lg overflow-hidden">
                    <table className="min-w-full leading-normal">
                        <thead>
                            <tr>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                    Firma Adı
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                    Adres
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                    Telefon Numarası
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                    Fax Numarası
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                    Email Adresi
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                    Sektör
                                </th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                    Açıklama
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            {companies && companies.map((company: Company) => (
                                <tr key={company.id}>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {/* Firma Adı */}
                                        <div className="flex items-center">
                                            {/* Render firma adı */}
                                            <div className="ml-3">
                                                <p className="text-gray-900 whitespace-no-wrap">{company.name}</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                    
                                        <p className="text-gray-900 whitespace-no-wrap">{company.address}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                  
                                        <p className="text-gray-900 whitespace-no-wrap">{company.phoneNumber}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                  
                                        <p className="text-gray-900 whitespace-no-wrap">{company.faxNumber}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                 
                                        <p className="text-gray-900 whitespace-no-wrap">{company.email}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                              
                                        <p className="text-gray-900 whitespace-no-wrap">{company.scope}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                     
                                        <p className="text-gray-900 whitespace-no-wrap">{company.description}</p>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>          


        </div>
    );
}

export default Companies;