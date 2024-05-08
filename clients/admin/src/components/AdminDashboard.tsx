import React from "react";
import CircleItem from "./dashboard-items/CircleItem";
import SquareItem from "./dashboard-items/SquareItem";

const AdminDashboard = () => {
  return (
    <section className="min-h-screen w-full px-10  ">
      {/* Computer Infos */}
      <ul className="flex flex-wrap justify-center gap-x-10">
        <li>
          <CircleItem title="RAM" freeSpace={16.2} totalSpace={30.65} />
        </li>
        <li>
          <CircleItem title="CPU" percentage={80} />
        </li>
        <li>
          <CircleItem
            title="Storage"
            percentage={undefined}
            freeSpace={700}
            totalSpace={1000}
          />
        </li>
      </ul>
      {/* User Infos */}
      <ul className="flex flex-wrap justify-center gap-10">
        <li>
          <SquareItem title="Kullanıcı Sayısı" count={206} />
        </li>
        <li>
          <SquareItem title="Kullanıcı Sayısı" count={206} />
        </li>
        <li>
          <SquareItem title="Kullanıcı Sayısı" count={206} />
        </li>
        <li>
          <SquareItem title="Kullanıcı Sayısı" count={206} />
        </li>
      </ul>
    </section>
  );
};

export default AdminDashboard;
