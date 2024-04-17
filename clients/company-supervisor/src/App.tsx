import { useEffect, useState } from 'react';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';

import { Header } from './components/Header';
import { Footer } from './components/Footer';
import { Root } from './routes/Root';
import { CompanyPage } from './routes/Company';
import ErrorPage from './error-page';
import CompanySupervisorProfile from './components/CompanySupervisorProfile';

const HeaderLayout = () => (
  <div className="flex min-h-screen flex-col justify-between">
    <Header />
    <div className="flex-grow">
      <Outlet />
    </div>
    <Footer />
  </div>
);

const App: React.FC = () => {
  const root_path: string | undefined = process.env.PUBLIC_URL;

  const router = createBrowserRouter([
    {
      element: <HeaderLayout />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: `${root_path}/`,
          element: <Root/>,
        },
        {
          path: `${root_path}/company`,
          element: <CompanyPage />,
        },
        {
          path: `${root_path}/profile`,
          element: <CompanySupervisorProfile />,
        },
      ],
    },
  ]);

  return (
    <div className="max-w-screen  min-h-screen w-screen justify-center">
      <RouterProvider router={router} />
    </div>
  );
};

export default App;
