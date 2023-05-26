import React, { useState } from 'react';
import { Outlet, NavLink } from 'react-router-dom';
export const Header = () => {
  const [burgerButton, setBurgerButton] = useState<boolean>(false);
  const public_url : string = process.env.PUBLIC_URL
  return (
    <header>
      <div className="header-content">
        <div className="header-buttons">
          <button className="header-button">Profil</button>
          <button className="header-button">Çıkış</button>
        </div>
        <nav className="bg-white dark:bg-gray-800  shadow ">
          <div className="px-8 mx-auto w-screen">
            <div className="flex items-center justify-between h-16">
              <div className=" flex items-center">
                <NavLink to={`${public_url}/`}>
                  <img className="w-14 h-14" src="/1.png" alt="Workflow" />
                </NavLink>
                <div className="hidden md:block">
                  <div className="flex items-baseline ml-10 space-x-4">
                    <NavLink to={`${public_url}/internships`}>
                      {({ isActive }) => (
                        <p
                          className={`${isActive ? `text-gray-200` : `text-gray-400`
                            } hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                        >
                          Stajlar
                        </p>
                      )}
                    </NavLink>
                    <NavLink to={`${public_url}/company`}>
                      {({ isActive }) => (
                        <p
                          className={`${isActive ? `text-gray-200` : `text-gray-400`
                            }  hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                        >
                          Şirket Bilgileri
                        </p>
                      )}
                    </NavLink>
                    <NavLink to={`${public_url}/help`}>
                      {({ isActive }) => (
                        <p
                          className={`${isActive ? `text-gray-200` : `text-gray-400`
                            }  hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                        >
                          Yardım
                        </p>
                      )}
                    </NavLink>
                    <NavLink to={`${public_url}/joker`}>
                      {({ isActive }) => (
                        <p
                          className={`${isActive ? `text-gray-200` : `text-gray-400`
                            }  hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                        >
                          Joker
                        </p>
                      )}
                    </NavLink>
                  </div>
                </div>
              </div>
              <div className="block">
                <div className="flex items-center ml-4 md:ml-6"></div>
              </div>
              <div className="flex -mr-2 md:hidden">
                <button
                  onClick={() => {
                    setBurgerButton(!burgerButton);
                  }}
                  className="text-gray-800 dark:text-white hover:text-gray-300 inline-flex items-center justify-center p-2 rounded-md focus:outline-none"
                >
                  <svg
                    width="20"
                    height="20"
                    fill="currentColor"
                    className="w-8 h-8"
                    viewBox="0 0 1792 1792"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M1664 1344v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45zm0-512v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45zm0-512v128q0 26-19 45t-45 19h-1408q-26 0-45-19t-19-45v-128q0-26 19-45t45-19h1408q26 0 45 19t19 45z"></path>
                  </svg>
                </button>
              </div>
            </div>
          </div>
          {burgerButton ? (
            <></>
          ) : (
            <div className="md:hidden">
              <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
                <NavLink to={`${public_url}/internships`}>
                  {({ isActive }) => (
                    <p
                      className={`${isActive ? `text-gray-800` : `text-gray-300`
                        } hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                    >
                      Stajlar
                    </p>
                  )}
                </NavLink>
                <NavLink to={`${public_url}/company`}>
                  {({ isActive }) => (
                    <p
                      className={`${isActive ? `text-gray-800` : `text-gray-300`
                        }  hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                    >
                      Şirket Bilgileri
                    </p>
                  )}
                </NavLink>
                <NavLink to={`${public_url}/help`}>
                  {({ isActive }) => (
                    <p
                      className={`${isActive ? `text-gray-800` : `text-gray-300`
                        }  hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                    >
                      Yardım
                    </p>
                  )}
                </NavLink>
                <NavLink to={`${public_url}/joker`}>
                  {({ isActive }) => (
                    <p
                      className={`${isActive ? `text-gray-800` : `text-gray-300`
                        }  hover:text-gray-800 dark:hover:text-white px-3 py-2 rounded-md text-sm font-medium`}
                    >
                      Joker
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
