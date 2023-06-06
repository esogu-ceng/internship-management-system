import { useRouteError } from 'react-router-dom';

export default function ErrorPage() {
  const error: any = useRouteError();
  console.error(error);
  return (
    <div id="error-page">
      <div className="relative h-screen w-screen overflow-hidden bg-indigo-900">
        <img
          src="error.jpg"
          className="absolute h-full w-screen object-cover"
        />
        <div className="absolute inset-0 bg-black opacity-25"></div>
        <div className="container relative z-10 mx-auto flex items-center px-6 py-32 md:px-12 xl:py-40">
          <div className="relative z-10 flex w-full flex-col items-center font-mono">
            <h1 className="mt-4 text-center text-5xl font-extrabold leading-tight text-white">
              Aradığınız sayfa bulunamadı
            </h1>
            <p className="my-44 animate-bounce text-8xl font-extrabold text-white">
              <i>{error.statusText || error.message}</i>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
