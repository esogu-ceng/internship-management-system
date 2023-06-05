import React, { useEffect, useState } from 'react';
import { InternshipDocumentType } from '../types/InternshipDocumentType';
export const InternshipDocument = ({
  internship_id,
}: {
  internship_id: number;
}) => {
  const [loading, setLoading] = useState(true);
  const [internshipDocuments, setInternshipDocuments] = useState<
    InternshipDocumentType[]
  >([]);
  const [documentName, setDocumentName] = useState<string>('loading');
  const [documentContents, setDocumentContents] = useState<string>('loading');
  useEffect(() => {
    fetch(`api/internshipdocument/internship/${internship_id}`)
      .then((response) => response.json())
      .then((data) => {
        setInternshipDocuments(data.content);
        setDocumentName(data.content[0].name);
        setDocumentContents(data.content[0].filePath);
        console.log(data);
      });
    setLoading(false);
  }, []);

  return loading ? (
    <div>loading...</div>
  ) : (
    <div className="w-6xl test flex flex-col justify-center md:flex-row">
      <div className="container mx-auto px-4 sm:px-8 md:w-1/2">
        <div className="py-8">
          <div className="-mx-4 overflow-x-auto px-4 py-4 sm:-mx-8 sm:px-8">
            <div className="inline-block min-w-full overflow-hidden rounded-lg shadow">
              <table className="min-w-full leading-normal">
                <thead>
                  <tr>
                    <th
                      scope="col"
                      className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                    >
                      Dosya Adı
                    </th>
                    <th
                      scope="col"
                      className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-bold uppercase text-gray-800"
                    >
                      Dosya Tipi
                    </th>
                    <th
                      scope="col"
                      className="border-b border-gray-200 bg-white px-5 py-3 text-left text-sm font-normal uppercase text-gray-800"
                    ></th>
                  </tr>
                </thead>
                <tbody>
                  {internshipDocuments.map((internshipDocument, index) => (
                    <tr key={index}>
                      <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                        <div className="flex items-center">
                          <div className="ml-3">
                            <p className="whitespace-no-wrap text-gray-900">
                              {internshipDocument.name}
                            </p>
                          </div>
                        </div>
                      </td>
                      <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                        <p className="whitespace-no-wrap text-gray-900">
                          {internshipDocument.type}
                        </p>
                      </td>
                      <td className="border-b border-gray-200 bg-white px-5 py-5 text-sm">
                        <button
                          onClick={() => {
                            setDocumentName(internshipDocument.name);
                            setDocumentContents(internshipDocument.filePath);
                          }}
                          type="button"
                          className="w-full rounded-lg  bg-indigo-600 px-4 py-2 text-center text-base font-semibold text-white shadow-md transition duration-200 ease-in hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2  focus:ring-offset-indigo-200 "
                        >
                          İçerik
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div className="py-8 md:w-1/2">
        <div className=" h-full rounded-2xl bg-white py-4 shadow-lg">
          <div className="flex flex-col items-center justify-center">
            <p className="mb-4 mt-4 text-center text-xl font-medium text-gray-900">
              {documentName}
            </p>
            <p className="px-2 text-start text-gray-700">{documentContents}</p>
          </div>
        </div>
      </div>
    </div>
  );
};
