import { useEffect, useState } from 'react';
import { InternshipRow } from '../components/InternshipRow';
import { Internship } from '../types/InternshipType';
import { PaginationButton } from '../components/PaginationButton';

export const Root = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [internships, setInternships] = useState<Internship[]>([]);
  const [currentSearch, setCurrentSearch] = useState('');

  const [totalPages, setTotalPages] = useState<number>(0);
  const [currentPage, setCurrentPage] = useState(1);
  const pageNumbers = [];

  useEffect(() => {
    fetchInternships(currentPage);
  }, []);

  const fetchInternships = (number: number) => {

    fetch(`/api/internships?pageNo=${number - 1}`, {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setLoading(false);
        setInternships(data.content);
        setTotalPages(data.totalPages);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  for (let i = 1; i <= Math.ceil(totalPages); i++) {
    pageNumbers.push(i);
  }

  const handleClick = (pageNumber: number) => {
    setCurrentPage(pageNumber);
    fetchInternships(pageNumber);
  };

  const decreasePageNumber = () => {
    if (currentPage != 1) {
      handleClick(currentPage - 1);
    }
  };

  const increasePageNumber = () => {
    if (currentPage != totalPages) {
      handleClick(currentPage + 1);
    }
  };

  return (
    <div>
      <div className="flex flex-col items-center justify-between">
        <div className="max-w-screen container mx-auto px-4 sm:px-8">
          <div className="py-8">
            <div className="mb-1 flex w-full flex-row justify-between sm:mb-0">
              <h2 className="text-2xl leading-tight">Stajlar</h2>
              <div className="text-end">
                <form className="flex w-3/4 max-w-sm flex-col justify-center space-y-3 md:w-full md:flex-row md:space-x-3 md:space-y-0">
                  <div className=" relative ">
                    <input
                      type="text"
                      id='"form-subscribe-Filter'
                      className=" w-full flex-1 appearance-none rounded-lg border border-gray-300  bg-white px-4 py-2 text-base text-gray-700 placeholder-gray-400 shadow-sm focus:border-transparent focus:outline-none focus:ring-2 focus:ring-blue-600"
                      placeholder="Öğrenci Ara"
                      onChange={(e) => setCurrentSearch(e.target.value)}
                    />
                  </div>
                </form>
              </div>
            </div>
            <div className="-mx-4 overflow-x-auto px-4 py-4 sm:-mx-10 sm:px-10">
              <div className="inline-block min-w-full overflow-hidden rounded-lg shadow">
                <table className="min-w-full leading-normal">
                  <thead>
                    <tr>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Öğrenci Adı
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Öğrenci Soyadı
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Gün
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Başlangıç Zamanı
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Bitiş Zamanı
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Durum
                      </th>
                      <th
                        scope="col"
                        className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                      >
                        Seçenekler
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {internships
                      ?.filter((int) => {
                        if (
                          int.student.surname
                            .toLowerCase()
                            .includes(currentSearch.toLowerCase()) ||
                          int.student.name
                            .toLowerCase()
                            .includes(currentSearch.toLowerCase()) ||
                          `${int.student.name} ${int.student.surname}`
                            .toLowerCase()
                            .includes(currentSearch.toLowerCase())
                        ) {
                          return true;
                        } else {
                          return false;
                        }
                      })
                      .map((int: Internship) => (
                        <InternshipRow
                          key={int.id}
                          internship={int}
                        />
                      ))}
                  </tbody>
                </table>
                <div className="xs:flex-row xs:justify-between flex flex-col items-center bg-white px-5 py-5">
                  <div className="flex items-center">
                    <button
                      type="button"
                      className="w-full rounded-l-xl border bg-white p-4 text-base text-gray-600 hover:bg-gray-100"
                      onClick={() => decreasePageNumber()}
                    >
                      <svg
                        width="9"
                        fill="currentColor"
                        height="8"
                        className=""
                        viewBox="0 0 1792 1792"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path d="M1427 301l-531 531 531 531q19 19 19 45t-19 45l-166 166q-19 19-45 19t-45-19l-742-742q-19-19-19-45t19-45l742-742q19-19 45-19t45 19l166 166q19 19 19 45t-19 45z"></path>
                      </svg>
                    </button>
                    {pageNumbers.map((number) => (
                      <PaginationButton
                        key={number}
                        number={number}
                        isActive={number == currentPage}
                        handleClick={() => handleClick(number)}
                      />
                    ))}
                    <button
                      type="button"
                      className="w-full rounded-r-xl border-b border-r border-t bg-white p-4 text-base text-gray-600 hover:bg-gray-100"
                      onClick={() => increasePageNumber()}
                    >
                      <svg
                        width="9"
                        fill="currentColor"
                        height="8"
                        className=""
                        viewBox="0 0 1792 1792"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path d="M1363 877l-742 742q-19 19-45 19t-45-19l-166-166q-19-19-19-45t19-45l531-531-531-531q-19-19-19-45t19-45l166-166q19-19 45-19t45 19l742 742q19 19 19 45t-19 45z"></path>
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
