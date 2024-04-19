import React, { useEffect, useState } from 'react';
import { Document } from '../types/DocumentType';
import axios from 'axios';

type ModalProps = {
	_internshipId: number;
	isOpen: boolean;
	onClose: () => void;
	children?: React.ReactNode;
};

interface PageableResponse<T> {
	content: T[];
}

const InternshipDocuments: React.FC<ModalProps> = ({ _internshipId, isOpen, onClose, children }) => {
	const [documents, setDocuments] = useState<Document[] | any>([]);
	const [loading, setLoading] = useState<boolean>(true);

	const fetchInternship = async () => {
		try {
			const response = await axios.get(`/api/internshipdocument/internship/${_internshipId}`, {
			});
			const { content } = response.data as PageableResponse<Document>;
			setDocuments(content);
			setLoading(false);
		} catch (error) {
			console.error(error);
			console.log("Hata Buradaa");
		}
	};

	useEffect(() => {
		const fetchData = async () => {
			try {
				const response = await axios.get(`/api/internshipdocument/internship/${_internshipId}`);
				const { content } = response.data as PageableResponse<Document>;
				setDocuments(content);
				setLoading(false);
			} catch (error) {
				console.error(error);
				console.log("Hatayı burada alıyorsun.");
				setLoading(false); // Hata durumunda da loading durumunu false yap
			}
		};

		fetchData();
	}, [_internshipId]);

	if (loading) {
		return <div>Loading...</div>;
	}

	function DocumentOpen(_fileId: number, _document: Document) {
		axios.get(`/api/internshipdocument/download?fileId=${_fileId}`, {
			responseType: 'blob' // Yanıt tipini belirt
		})
			.then(response => {
				const url = URL.createObjectURL(new Blob([response.data]));
				const link = document.createElement('a');
				link.href = url;
				link.setAttribute('download', _document.name + '.' + _document.type);
				document.body.appendChild(link);
				link.click();
				URL.revokeObjectURL(url);
			})
			.catch(error => {
				console.error(error);
				window.alert('Staj belgesi bulunamadı.');
				console.log('Staj belgesi bulunamadı.');
			});
	}


	return (
		<div className="bg-white p-5 rounded-md w-full pt-0">
			<div className="flex items-center justify-between pb-6">
				<div>
					<h1 className="font-bold text-4xl font-semibold mb-4">Staj Dokümanları</h1>
				</div>
			</div>
			<div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
				<div className="inline-block min-w-full shadow rounded-lg overflow-hidden">
					<div className="max-h-80 overflow-y-auto">
						<table className="min-w-full leading-normal">
							<thead>
								<tr>
									<th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
										Doküman Adı
									</th>
									<th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
										Doküman Tipi
									</th>
									<th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
										Doküman
									</th>
								</tr>
							</thead>
							<tbody>
								{documents.map((document: Document) => (
									<tr key={document.id}>
										<td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
											<div className="flex items-center">
												<div className="ml-3">
													<p className="text-gray-900 whitespace-no-wrap">{document.name}</p>
												</div>
											</div>
										</td>
										<td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
											<p className="text-gray-900 whitespace-no-wrap">{document.type}</p>
										</td>
										<td className="px-5 py-5 border-b border-gray-200 bg-white text-sm text-right">
											<div>
												<button className="border border-blue-500 text-blue-500 rounded-md px-4 py-2 transition duration-300 ease-in-out hover:bg-blue-500 hover:text-white" onClick={() => DocumentOpen(document.id, document)}> AÇ </button>
											</div>
										</td>
									</tr>
								))}
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	);
};

export default InternshipDocuments;
