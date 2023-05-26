import { useState } from 'react';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';

import { Header } from './components/Header';
import { Footer } from './components/Footer';
import CompanyDashboard from './components/CompanyDashboard';
import { Root } from './routes/Root';
import { CompanyPage } from './routes/Company';
import ErrorPage from './error-page';

const HeaderLayout = () => (
  <div className="flex flex-col min-h-screen justify-between">
    <header>
      <Header />
    </header>
    <div className="flex-grow">
      <Outlet />
    </div>
    <footer>
      <Footer />
    </footer>
  </div>
);

const App: React.FC = () => {
  //TODO: UPDATE HERE DYNAMICALLY
  const [currentCompanyId, setcurrentCompanyId] = useState<number>(250);
  const [auth, setAuth] = useState<string>('ykartal@ogu.edu.tr:sdfasdfadfasdfasdfasdf');
  //TODO end  
  const public_url : string = process.env.PUBLIC_URL

  const router = createBrowserRouter([
    {
      element: <HeaderLayout />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: `${public_url}/`,
          element: <CompanyDashboard />,
        },
        {
          path: `${public_url}/internships`,
          element: <Root _companyId={currentCompanyId} _auth={auth}/>,
        },
        {
          path: `${public_url}/company`,
          element: <CompanyPage  _companyId={currentCompanyId} _auth={auth}/>,
        },
      ],
    },
  ]);
  
  return (
    <div className="min-h-screen flex justify-center w-screen max-w-screen">
      <RouterProvider router={router} />
    </div>
  );
};

export default App;
