import React, { useEffect, useState } from 'react';
import { CompanySupervisor } from '../types/CompanySupervisorType';
function CompanySupervisorProfile() {
    const [companySupervisorDatas, setCompanySupervisorDatas] = useState<CompanySupervisor[] | null>(null);


    useEffect(() => {
        fetch('/api/user/company-supervisor/auth', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                console.log('data: ', data);
                const companySupervisorData = {
                    ...data,
                    createDate: new Date(data.createDate),
                };
                setCompanySupervisorDatas([companySupervisorData]);

                fetch('/api/company-supervisor/getCompanySupervisorByUserId/' + companySupervisorData.id, {
                    method: 'GET'
                  })
                    .then(response => response.json())
                    .then(data => {
                      console.log('data: ', data);
                      const updatedCompanySupervisorData = {
                        ...companySupervisorData,
                        ...data,
                        companyId: data.company.id,
                        companyName: data.company.name,
                        createDate: new Date(data.createDate),
                      };
                      setCompanySupervisorDatas([updatedCompanySupervisorData]);
                  
                      fetch('/api/company/' + data.company.id, {
                        method: 'GET'
                      })
                        .then(response => response.json())
                        .then(companyData => {
                          console.log('companyData: ', companyData);
                          const finalCompanySupervisorData = {
                            ...updatedCompanySupervisorData,
                            companyName: companyData.name,
                          };
                          setCompanySupervisorDatas([finalCompanySupervisorData]);
                        })
                        .catch(error => {
                          console.log(error);
                        });
                    })
                    .catch(error => {
                      console.log(error);
                    });
                  
            })
            .catch(error => {
                console.log(error);
            });
    }, []);


    return (
        <div className="flex flex-col justify-content-flex-start pt-10 items-center h-screen w-screen bg-gray-100">
            <div className="relative flex flex-col justify-content-flex-start rounded-[20px] w-[700px] max-w-[95%] mx-auto bg-white bg-clip-border shadow-3xl shadow-shadow-500 p-3">
                <div className="mt-2 mb-8 w-full">
                    <h4 className="px-2 text-xl text-center font-bold text-navy-700">Profil Bilgileri</h4>
                </div>

                {companySupervisorDatas &&
                    companySupervisorDatas.map(companySupervisorData => (
                        <div key={companySupervisorData.email} className="grid grid-cols-2 gap-4 px-2 w-full">
                            <div
                                className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">İsim </p>
                                <p className="text-base font-medium text-navy-700">
                                    {companySupervisorData.name}
                                </p>
                            </div>

                            <div
                                className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Soyisim</p>
                                <p className="text-base font-medium text-navy-700">
                                    {companySupervisorData.surname}
                                </p>
                            </div>

                            <div
                                className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Telefon Numarası</p>
                                <p className="text-base font-medium text-navy-700">
                                    {companySupervisorData.phoneNumber}
                                </p>
                            </div>

                            <div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Email</p>
                                <p className="text-base font-medium text-navy-700">
                                    {companySupervisorData.email}
                                </p>
                            </div>

                            <div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Şirket</p>
                                <p className="text-base font-medium text-navy-700">
                                    {companySupervisorData.companyName}
                                </p>
                            </div>

                            <div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Kullanıcı Tipi</p>
                                <p className="text-base font-medium text-navy-700">
                                    {companySupervisorData.userType}
                                </p>
                            </div>

                            <div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Kullanıcı Oluşturulma Tarihi</p>
                                <p className="text-base font-medium text-navy-700">
                                    {companySupervisorData.createDate.toLocaleDateString()}</p>
                            </div>
                        </div>

                    ))}
            </div>
        </div>
    );
}

export default CompanySupervisorProfile;