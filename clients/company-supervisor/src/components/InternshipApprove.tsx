import React, { useState, useEffect } from "react";


const InternshipApprove = ({ id, onClosePopUp, handleInternshipStatusComplete }: { id: number, onClosePopUp: () => void, handleInternshipStatusComplete: () => void }) =>  {
    const [confirmationMessage, setConfirmationMessage] = useState("Staj Başvurusunu Onaylamak İstediğinize Emin Misiniz?");

    const handleConfirm = async () => {
        try {
            console.log("handle confirm");
            const response = await fetch(`/api/internship/approve/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            console.log(data);
            setConfirmationMessage("Staj Başvurusu Onaylandı");


        } catch (error) {
            console.error('Hata:', error);
        }
    };
    const handleClose = () => {
        onClosePopUp(); // Call the onClosePopUp function passed from the parent component


        handleInternshipStatusComplete();




    };
    return (
        <div>
            <div className="flex flex-col items-center rounded-lg bg-white p-8">
                <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">{confirmationMessage}</h2>
                <div className="flex justify-center mt-4">
                    {confirmationMessage !== "Staj Başvurusu Onaylandı" && (
                        <button
                            className="rounded bg-indigo-500 px-4 py-2 font-bold text-white hover:bg-indigo-600"

                            onClick={handleConfirm}
                        >
                            Onayla
                        </button>)}


                    <button
                        className="ml-4 rounded bg-gray-500 px-4 py-2 font-bold text-white hover:bg-gray-600"
                        onClick={handleClose} // Assuming you have a function to handle closing the popup
                    >
                        Kapat
                    </button>
                </div>
            </div>
        </div>
    );
}

export default InternshipApprove;
