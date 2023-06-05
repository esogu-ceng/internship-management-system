import { useEffect, useState } from 'react';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';

import { Header } from './components/Header';
import { Footer } from './components/Footer';
import CompanyDashboard from './components/CompanyDashboard';
import { Root } from './routes/Root';
import { CompanyPage } from './routes/Company';
import ErrorPage from './error-page';
import { User } from './types/UserType';
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
  const [user, setUser] = useState<User>();
  const [currentCompanyId, setcurrentCompanyId] = useState<number>(0);
  //TODO: UPDATE HERE DYNAMICALLY

  //TODO end
  const root_path: string | undefined = process.env.PUBLIC_URL;

  const router = createBrowserRouter([
    {
      element: <HeaderLayout />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: `${root_path}/`,
          element: <CompanyDashboard />,
        },
        {
          path: `${root_path}/internships`,
          element: <Root _companyId={currentCompanyId} />,
        },
        {
          path: `${root_path}/company`,
          element: <CompanyPage _companyId={currentCompanyId} />,
        },
        {
          path: `${root_path}/profile`,
          element: <CompanySupervisorProfile />,
        },
      ],
    },
  ]);

  function getAuthUser() {
    fetch('/api/user/company-supervisor/auth', {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setUser(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  function getCompanyId(userId: number | undefined) {
    fetch(`/api/company-supervisor/getCompanySupervisorByUserId/${userId}`, {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setcurrentCompanyId(data.company.id);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  useEffect(() => {
    getAuthUser();
  }, []);

  useEffect(() => {
    if (user) {
      getCompanyId(user?.id);
    }
  }, [user]);

  return (
    <div className="max-w-screen flex min-h-screen w-screen justify-center">
      <RouterProvider router={router} />
    </div>
  );
};

export default App;
