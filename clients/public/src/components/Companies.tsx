import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';
import "../style/company.css";

const Companies = () => {

    const [companies, setCompanies] = useState<Company[] | null>(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [filteredCompanies, setFilteredCompanies] = useState<Company[] | null>(null);

    useEffect(() => {
        fetch('/api/company/getAllCompanies', {
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
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    useEffect(() => {
        if (companies) {
            const filtered = companies.filter((company: Company) =>
                company.name.toLowerCase().includes(searchQuery.toLowerCase())
            );
            setFilteredCompanies(filtered);
        }
    }, [companies, searchQuery]);

    return (
        <div style={{ position: 'absolute', top: '10%', left: 0, width: '100%', backgroundColor: 'white' }}>
        <div style={{padding:'0 3%'}}>
            <span style={{lineHeight: 1.25, fontSize: 24}}>Firmalar</span>
            <input 
                type="text"
                placeholder="Firma Ara"
                style={{
                    float: 'right',
                    borderRadius: '0.375rem', /* 6px */
                    border: '1px solid #D1D5DB',
                    backgroundColor: 'white',
                    padding: '8px 16px', /* 4px 16px */
                    fontSize: '16px', /* 16px */
                    color: '#4B5563',
                    boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
                    outline: 'none',
                    boxSizing: 'border-box',
                }}
                onChange={(e) => setSearchQuery(e.target.value)}
                onFocus={(e) => {
                    e.target.style.borderColor = '#2563EB'; /* Odaklandığında border rengi */
                    e.target.style.borderWidth = '2px'; /* Odaklandığında border kalınlığı */
                }}
                onBlur={(e) => {
                    e.target.style.borderColor = '#D1D5DB'; /* Odaklanmadığında varsayılan border rengi */
                    e.target.style.borderWidth = '1px'; /* Odaklanmadığında varsayılan border kalınlığı */
                }}
                />
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
                            {filteredCompanies && filteredCompanies.map((company: Company) => (
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
