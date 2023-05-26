import { useEffect, useState } from 'react';
import { Header } from './components/Header';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import { Root } from './routes/Root';
import ErrorPage from './error-page';
import { CompanyPage } from './routes/Company';
import { Footer } from './components/Footer';
const HeaderLayout = () => (
  <div className="flex flex-col justify-between">
    <header>
      <Header />
    </header>
    <Outlet />
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

  const router = createBrowserRouter([
    {
      element: <HeaderLayout />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: '/companysupervisor',
          element: <Root _companyId={currentCompanyId} _auth={auth}/>,
        },
        {
          path: '/companysupervisor/company',
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
