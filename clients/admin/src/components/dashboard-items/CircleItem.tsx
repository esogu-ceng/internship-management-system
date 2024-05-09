import React, { FC } from "react";

interface Props {
  title: string;
  percentage?: number;
  totalSpace?: number;
  freeSpace?: number;
}

const CircleItem: FC<Props> = (props) => {
  let { title, percentage, totalSpace, freeSpace } = props;

  if (!percentage && totalSpace && freeSpace) {
    percentage = ((totalSpace - freeSpace) / totalSpace) * 100;
  }

  return (
    <div className="memory-meter">
      <svg viewBox="0 0 36 36" className="circular-chart blue">
        <path
          className="circle-bg"
          d="M18 2.0845
                    a 15.9155 15.9155 0 0 1 0 31.831"
          fill="none"
          stroke="#e6e6e6"
          strokeWidth="3.8"
        />
        <path
          className="circle"
          strokeDasharray={`${percentage! / 2}, 100`}
          d="M18 2.0845
                    a 15.9155 15.9155 0 0 1 0 31.831"
          fill="none"
          stroke="#0078d4"
          strokeWidth="3.8"
        />
      </svg>
      <div className="percentage-container">
        <p className="percentage">{percentage?.toFixed(2)}%</p>
        <p className="circle-title">{title}</p>
      </div>
      {totalSpace && freeSpace && (
        <div className="memory-info">{`${totalSpace.toFixed(
          2
        )} GB'ın ${freeSpace.toFixed(2)} GB'ı boş`}</div>
      )}
    </div>
  );
};

export default CircleItem;
