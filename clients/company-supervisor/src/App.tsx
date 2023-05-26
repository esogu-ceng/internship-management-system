import React from 'react';
import { Header } from './components/Header';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import { Root } from './routes/Root';
import ErrorPage from './error-page';
import { Company } from './routes/Company';
import { Footer } from './components/Footer';
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
  const router = createBrowserRouter([
    {
      element: <HeaderLayout />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: '/',
          element: <Root />,
        },
        {
          path: 'Company',
          element: <Company />,
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
