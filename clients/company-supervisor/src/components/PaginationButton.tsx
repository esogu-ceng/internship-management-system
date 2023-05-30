import React from 'react';

export const PaginationButton = ({
  number,
  handleClick,
  isActive,
}: {
  number: number;
  handleClick: any;
  isActive: boolean;
}) => {
  return (
    <button
      type="button"
      className={`w-full px-4 py-2 text-base text-indigo-500  border-t border-b hover:bg-gray-100 ${
        isActive ? 'bg-indigo-100' : 'bg-white'
      }`}
      onClick={handleClick}
    >
      {number}
    </button>
  );
};
