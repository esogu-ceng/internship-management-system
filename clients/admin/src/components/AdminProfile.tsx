import React, { useEffect, useState } from 'react';
import { Admin } from '../Types/AdminType';
function AdminProfile() {
  const [adminDatas, setAdminDatas] = useState<Admin[] | null>(null);

  useEffect(() => {
    fetch('/api/user/admin/auth', {
      method: 'GET'
    })
      .then(response => response.json())
      .then(data => {
        console.log('data: ', data);
        const adminData = {
          ...data,
          createDate: new Date(data.createDate),
        };
        setAdminDatas([adminData]);
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

        {adminDatas &&
          adminDatas.map(adminData => (
            <div className="grid grid-cols-2 gap-4 px-2 w-full">
              <div

                className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                <p className="text-sm text-gray-600">Kullanıcı Adı</p>
                <p className="text-base font-medium text-navy-700">
                  {adminData.username}
                </p>
              </div>

              <div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                <p className="text-sm text-gray-600">Email</p>
                <p className="text-base font-medium text-navy-700">
                  {adminData.email}
                </p>
              </div>

              <div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                <p className="text-sm text-gray-600">Kullanıcı Tipi</p>
                <p className="text-base font-medium text-navy-700">
                  {adminData.userType}
                </p>
              </div>

              <div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                <p className="text-sm text-gray-600">Kullanıcı Oluşturulma Tarihi</p>
                <p className="text-base font-medium text-navy-700">
                  {adminData.createDate.toLocaleDateString()}</p>
              </div>
            </div>

          ))}
      </div>
    </div>
  );
}

export default AdminProfile;
