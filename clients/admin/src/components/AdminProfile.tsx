import React, { useEffect, useState } from 'react';
import { Admin } from '../types/AdminType';

function AdminProfile() {
  const [adminDatas, setAdminDatas] = useState<Admin[] | null>(null);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");
  const [contactNumber, setContactNumber] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    fetch("/api/user/admin/auth", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("data: ", data);
        const adminData = {
          ...data,
          createDate: new Date(data.createDate),
        };
        setAdminDatas([adminData]);
        setFirstName(adminData.firstName);
        setLastName(adminData.lastName);
        setEmail(adminData.email);
        setAddress(adminData.address);
        setContactNumber(adminData.contactNumber);
        setCity(adminData.city);
        setState(adminData.state);
        setPassword(adminData.password);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className="flex justify-center items-center px-16 py-50 bg-white max-md:px-5">
      <div className="mt-10 w-full max-w-[1237px] max-md:max-w-full">
        <div className="flex gap-5 max-md:flex-col max-md:gap-0">
          <div className="flex flex-col w-[37%] max-md:ml-0 max-md:w-full">
            <div className="flex flex-col self-stretch px-0.5 pb-11 my-auto w-full bg-violet-50 rounded-2xl shadow-lg max-md:mt-10 max-md:max-w-full">
              <div className="flex z-10 flex-col items-center px-24 p-5 -mt-1.5 bg-indigo-600 rounded-2xl shadow-lg max-md:px-5 max-md:max-w-full">
                <div className="flex overflow-hidden relative z-10 flex-col justify-center items-center px-1.5 py-1 mb-0 max-w-full aspect-square fill-indigo-600 w-[206px] max-md:mb-2.5">
                  <img
                    loading="lazy"
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/0a8f0f3b7decb4450d81463b68d72f60548b8234c93b8b89b321fd61d9ac5d4e?"
                    className="object-cover absolute inset-0 size-full"
                  />
                  <img
                    loading="lazy"
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/2c6ae9284908995684cb0ddf8c2c274c75e51174af685e2ab2c27d21cdc9d3da?"
                    className="w-full aspect-square fill-white"
                  />
                </div>
              </div>
              <div className="flex flex-col self-start mt-12 ml-11 text-xl font-semibold text-zinc-900 max-md:mt-10 max-md:ml-2.5">
                <div className="self-end text-4xl text-black">İsim Soyisim: {firstName} {lastName}</div>
                <div className="mt-6">Email: {email}</div>
                <div className="mt-4">Address: {address}</div>
                <div className="mt-4">Contact Number: {contactNumber}</div>
                <div className="mt-4">City/State: {city}/{state}</div>
              </div>
            </div>
          </div>
          <div className="flex flex-col ml-5 w-[63%] max-md:ml-0 max-md:w-full">
            <div className="flex flex-col grow py-7 w-full bg-violet-50 rounded-2xl shadow-lg max-md:mt-5 max-md:max-w-full">
              <div className="self-center text-4xl font-semibold text-black">
                Edit profile
              </div>
              <div className="flex flex-col px-11 mt-7 max-md:px-5 max-md:max-w-full">
                <div className="flex gap-5 max-md:flex-wrap">
                  <div className="flex flex-col flex-1 grow shrink-0 basis-0 w-fit">
                    <div className="text-xl font-semibold text-zinc-900">
                      First Name
                    </div>
                    <input 
                      className="justify-center items-start p-6 mt-2.5 text-lg font-medium whitespace-nowrap bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:px-5"
                      value={firstName}
                      onChange={(e) => setFirstName(e.target.value)}
                    />              
                  </div>
                  <div className="flex flex-col flex-1 grow shrink-0 basis-0 w-fit">
                    <div className="text-xl font-semibold text-zinc-900">
                      Last Name
                    </div>
                    <input 
                      className="justify-center items-start p-6 mt-2.5 text-lg font-medium whitespace-nowrap bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:px-5"
                      value={lastName}
                      onChange={(e) => setLastName(e.target.value)}
                    /> 
                  </div>
                </div>
                <div className="mt-5 text-xl font-semibold text-zinc-900 max-md:max-w-full">
                  Email
                </div>
                <input 
                  className="flex gap-5 px-6 py-4 mt-2.5 text-lg font-medium whitespace-nowrap bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:flex-wrap max-md:px-5 max-md:max-w-full"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
                <div className="mt-5 text-xl font-semibold text-zinc-900 max-md:max-w-full">
                  Address
                </div>
                <input 
                  className="justify-center items-start p-6 mt-2.5 text-lg font-medium bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:px-5 max-md:max-w-full"
                  value={address}
                  onChange={(e) => setAddress(e.target.value)}
                />
                <div className="mt-6 text-xl font-semibold text-zinc-900 max-md:max-w-full">
                  Contact Number
                </div>
                <input 
                  className="justify-center items-start p-6 mt-2.5 text-lg font-medium whitespace-nowrap bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:px-5 max-md:max-w-full"
                  value={contactNumber}
                  onChange={(e) => setContactNumber(e.target.value)}
                />
                <div className="mt-5 max-md:max-w-full">
                  <div className="flex gap-5 max-md:flex-col max-md:gap-0">
                    <div className="flex flex-col w-6/12 max-md:ml-0 max-md:w-full">
                      <div className="flex flex-col grow text-xl font-semibold whitespace-nowrap text-zinc-900 max-md:mt-10">
                        <div>City</div>
                        <input 
                          className="flex gap-5 justify-between px-4 py-2.5 mt-1.5 text-lg font-medium bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:pl-5"
                          value={city}
                          onChange={(e) => setCity(e.target.value)}
                        />
                      </div>
                    </div>
                    <div className="flex flex-col ml-5 w-6/12 max-md:ml-0 max-md:w-full">
                      <div className="flex flex-col whitespace-nowrap max-md:mt-10">
                        <div className="text-xl font-semibold text-zinc-900">
                          State
                        </div>
                        <input
                          className="flex gap-5 justify-between px-4 py-2.5 mt-1.5 text-lg font-medium bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:pl-5"
                          value={state}
                          onChange={(e) => setState(e.target.value)}
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div className="mt-6 text-xl font-semibold text-zinc-900 max-md:max-w-full">
                  Password
                </div>
                <input
                  className="flex gap-5 px-6 py-4 mt-2.5 text-lg font-medium bg-white rounded border-2 border-solid border-zinc-500 text-zinc-500 max-md:flex-wrap max-md:px-5 max-md:max-w-full"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <div className="flex gap-5 justify-between self-center mt-8 text-xl whitespace-nowrap">
                  <div className="justify-center px-9 py-3.5 text-indigo-600 bg-white rounded border-2 border-indigo-600 border-solid max-md:px-5">
                    Cancel
                  </div>
                  <div className="justify-center px-12 py-3.5 font-semibold text-white bg-indigo-600 rounded max-md:px-5">
                    Save
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>  
    </div> 
  );
}

export default AdminProfile;
