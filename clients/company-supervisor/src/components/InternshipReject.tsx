import React, {useState} from "react";

const InternshipReject= ({ id, onClosePopUp, handleInternshipStatusComplete }: { id: number, onClosePopUp: () => void, handleInternshipStatusComplete: () => void }) =>  {

    const [confirmationMessage, setConfirmationMessage] = useState("Staj başvurusunu reddetmek istediğinize emin misiniz?");
    const handleReject = async () => {
        try {
            console.log("handle reject");
            const response = await fetch(`/api/internship/reject/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            console.log(data);
            setConfirmationMessage("Staj Başvurusu Reddedildi");

        } catch (error) {
            console.error('Error:', error);
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
                <div className="flex justify-center">
                    {confirmationMessage !== "Staj Başvurusu Reddedildi" && (
                        <button
                            className="rounded bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-600 mr-4"
                            onClick={handleReject}
                        >
                            Reddet
                        </button>
                    )}
                    <button
                        className="rounded bg-gray-500 px-4 py-2 font-bold text-white hover:bg-gray-600"
                        onClick={handleClose}
                    >
                        Kapat
                    </button>
                </div>
            </div>
        </div>
    );
}

export default InternshipReject;
