import React, { useEffect, useState } from 'react';
import { Company } from '../types/CompanyType';
import "../style/company.css";

const Companies = () => {

    const [companies, setCompanies] = useState<Company[] | null>(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [filteredCompanies, setFilteredCompanies] = useState<Company[] | null>(null);
    const [internCounts, setInternCounts] = useState<[number, number][] | null>(null);


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
            // Get intern counts for filtered companies
            fetch('/api/internship/count/companyStudent', {
                method: 'GET'
            })
                .then(response => response.json())
                .then(data => {
                    setInternCounts(data);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }, [companies, searchQuery]);

    // Function to get intern count for a specific company
    const getInternCountForCompany = (companyId: number) => {
        if (internCounts) {
            const countData = internCounts.find(([id, count]) => id === companyId);
            if (countData) {
                return countData[1];
            }
        }
        return 0; // If no count data found, return 0
    }



    return (
        <div className="bg-white p-5 rounded-md w-full pt-0">
            <div className="flex items-center justify-between pb-6">
                <div className="header-firmalar">
                    <h1 className="font-bold text-4xl font-semibold mt-5">Firmalar</h1>
                </div>
            </div>
            <div className="flex items-center justify-between">
                <div className="flex bg-gray-50 items-center p-2 rounded-md">
                    <input
                        className="bg-gray-50 outline-none ml-1 block"
                        type="text"
                        placeholder="Search..."
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
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
                            <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                Stajyer Sayısı
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
                                <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                    <p className="text-gray-900 whitespace-no-wrap">{getInternCountForCompany(company.id)}</p>
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
