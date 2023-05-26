import { useEffect, useRef, useState } from 'react';
import { Company } from '../types/CompanyType';
import { CompanyRowProps } from '../types/CompanyRowProps';

export const CompanyRow = ({
    companyId,
    field,
    value,
    dataField,
    onEditClick,
    onSaveClick
}: CompanyRowProps ) => {

    const [editMode, setEditMode] = useState<boolean>(false);
    const [editedScope, setEditedScope] = useState<string | number | undefined>(value || '');
    const [isFieldChanged, setFieldChanged] = useState<boolean>(false);
    const [displayedValue, setDisplayedValue] = useState<string | number | undefined>(value);
    const textareaRef = useRef<HTMLTextAreaElement>(null);

    useEffect(() => {
        setDisplayedValue(value);
    }, [value]);

    const handleEditClick = () => {
        setEditedScope(displayedValue || '');
        setEditMode(true);
        setFieldChanged(false);
        onEditClick();

        setTimeout(() => {
            if (textareaRef.current) {
                textareaRef.current.focus();
                textareaRef.current.select();
            }
        }, 0);
    };

    const handleSaveClick = () => {
        if (isFieldChanged) {
            onSaveClick(dataField, editedScope);
            setDisplayedValue(editedScope);
        }
        setEditMode(false);
        setFieldChanged(false);
    };

    const handleCancelClick = () => {
        setEditMode(false);
        setFieldChanged(false);
    };

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setEditedScope(e.target.value);
        setFieldChanged(true);
    };

    const handleDoubleClick = () => {
        if (!editMode) {
            handleEditClick();
        }
    };

    return (
        <tr className={`text-gray-700 ${editMode ? 'border-2 border-[2px] border-indigo-500' : 'border border-22c55e'}`}>
            <td id = "fieldName" className="p-4 dark:border-dark-5" style={{ fontWeight: 'bold' }}>
                {field}
            </td>
            <td className="p-4 dark:border-dark-5">
                {editMode ? (
                    <textarea
                        id = "textEditField"
                        ref={textareaRef}
                        value={editedScope}
                        onChange={handleChange}
                        style={{ width: '100%', resize: 'vertical', maxWidth: '800px', minWidth: '800px', wordWrap: 'break-word'}}
                    />
                ) : (
                    <div
                        id = "textField"
                        style={{ maxWidth: '800px', minWidth: '800px', wordWrap: 'break-word', cursor: 'pointer' }}
                        onDoubleClick={handleDoubleClick}
                    >
                        {displayedValue}
                    </div>
                )}
            </td>
            <td className="p-4 dark:border-dark-5">
                {editMode ? (
                    <div>
                        <button
                        id = "save"
                            className="px-3 py-3 text-base rounded-full text-green-500 border border-green-500 undefined hover:bg-green-50 transition-colors"
                            onClick={handleSaveClick}
                        >
                            &#10004; {/* Checkmark Symbol */}
                        </button>

                        <button
                            id = "cancel"
                            className="px-3 py-3 text-base rounded-full text-red-500 border border-red-500 undefined hover:bg-red-50 transition-colors ml-2"
                            onClick={handleCancelClick}
                        >
                            &#10006; {/* Cross Mark Symbol */}
                        </button>

                    </div>
                ) : (
                    <a
                        id = "edit"
                        href="#"
                        className="px-4 py-2 text-base rounded-full text-indigo-500 border border-indigo-500 undefined hover:bg-indigo-50 transition-colors"
                        onClick={handleEditClick}
                    >
                        Düzenle
                    </a>
                )}
            </td>
        </tr>
    );
};
