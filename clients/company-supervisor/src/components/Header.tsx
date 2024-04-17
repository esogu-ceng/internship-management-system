import React, { useState } from 'react';
import { Outlet, NavLink } from 'react-router-dom';
export const Header = () => {
  const [burgerButton, setBurgerButton] = useState<boolean>(false);
  const root_path: string | undefined = process.env.PUBLIC_URL;

  return (
    <header>
      <div className="header-content ">
        <nav className="bg-white shadow  dark:bg-gray-800 ">
          <div className="mx-auto w-screen px-8">
            <div className="flex h-16 items-center justify-between">
              <div className=" flex items-center">
                <NavLink to={`${root_path}/`}>
                  <img className="h-14 w-14" src="1.png" alt="Workflow" />
                </NavLink>
                <div className="hidden md:block">
                  <div className="ml-10 flex items-baseline space-x-4">
                    <NavLink to={`${root_path}/`}>
                      {({ isActive }) => (
                        <p
                          className={`${
                            isActive ? `text-gray-700` : `text-gray-500`
                          } rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                        >
                          Stajlar
                        </p>
                      )}
                    </NavLink>
                    <NavLink to={`${root_path}/company`}>
                      {({ isActive }) => (
                        <p
                          className={`${
                            isActive ? `text-gray-700` : `text-gray-500`
                          }  rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                        >
                          Şirket Bilgileri
                        </p>
                      )}
                    </NavLink>
                    <NavLink to={`${root_path}/help`}>
                      {({ isActive }) => (
                        <p
                          className={`${
                            isActive ? `text-gray-700` : `text-gray-500`
                          }  rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                        >
                          Yardım
                        </p>
                      )}
                    </NavLink>
                  </div>
                </div>
              </div>
              <div className="flex">
                <div className="flex items-center">
                  <div className="ml-4 flex items-center md:ml-6">
                    <NavLink to={`${root_path}/profile`}>
                      {({ isActive }) => (
                        <p
                          className={`${
                            isActive ? `text-gray-700` : `text-gray-500`
                          }  rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                        >
                          Profil
                        </p>
                      )}
                    </NavLink>
                    <NavLink
                      reloadDocument
                      to={process.env.REACT_APP_API_BASE_URI + 'logout'}
                    >
                      {({ isActive }) => (
                        <p
                          className={`${
                            isActive ? `text-gray-700` : `text-gray-500`
                          }  rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                        >
                          Çıkış Yap
                        </p>
                      )}
                    </NavLink>
                  </div>
                </div>
                <div className="-mr-2 flex md:hidden">
                  <button
                    onClick={() => {
                      setBurgerButton(!burgerButton);
                    }}
                    className="inline-flex items-center justify-center rounded-md p-2 text-gray-900 hover:text-gray-300 focus:outline-none dark:text-white"
                  >
                    <svg
                      width="20"
                      height="20"
                      fill="currentColor"
                      className="h-8 w-8"
                      viewBox="0 0 1792 1792"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path d="M1664 1344v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45zm0-512v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45zm0-512v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45z"></path>
                    </svg>
                  </button>
                </div>
              </div>
            </div>
          </div>
          {burgerButton ? (
            <></>
          ) : (
            <div className="md:hidden">
              <div className="space-y-1 px-2 pb-3 pt-2 sm:px-3">
                <NavLink to={`${root_path}/internships`}>
                  {({ isActive }) => (
                    <p
                      className={`${
                        isActive ? `text-gray-900` : `text-gray-500`
                      } rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                    >
                      Stajlar
                    </p>
                  )}
                </NavLink>
                <NavLink to={`${root_path}/company`}>
                  {({ isActive }) => (
                    <p
                      className={`${
                        isActive ? `text-gray-900` : `text-gray-500`
                      }  rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                    >
                      Şirket Bilgileri
                    </p>
                  )}
                </NavLink>
                <NavLink to={`${root_path}/help`}>
                  {({ isActive }) => (
                    <p
                      className={`${
                        isActive ? `text-gray-900` : `text-gray-500`
                      }  rounded-md px-3 py-2 text-sm font-medium hover:text-gray-900 dark:hover:text-white`}
                    >
                      Yardım
                    </p>
                  )}
                </NavLink>
              </div>
            </div>
          )}
        </nav>
      </div>
    </header>
  );
};
