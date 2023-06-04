import React from 'react';

export const PaginationButton = ({
  number,
  handleClick,
}: {
  number: number;
  handleClick: any;
}) => {
  return (
    <button
      type="button"
      className="w-full px-4 py-2 text-base text-indigo-500 bg-white border-t border-b hover:bg-gray-100 "
      onClick={handleClick}
    >
      {number}
    </button>
  );
};
