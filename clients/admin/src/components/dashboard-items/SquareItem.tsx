import { UserOutlined } from "@ant-design/icons";
import { FC } from "react";

interface Props {
  title: string;
  count: number;
}

const SquareItem: FC<Props> = ({ title, count }) => {
  return (
    <div className="bg-gray-100 p-4 rounded-lg w-52">
      <div className="mb-4 flex items-center justify-between">
        <h3 className=" text-lg">{title}</h3>
        <UserOutlined />
      </div>
      <p className="text-4xl font-semibold">
        {count} <span className="text-lg font-light">kişi</span>
      </p>
    </div>
  );
};

export default SquareItem;
