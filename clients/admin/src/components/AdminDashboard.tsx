import CircleItem from "./dashboard-items/CircleItem";
import SquareItem from "./dashboard-items/SquareItem";
import useGetDashboardDatas from "../hooks/useGetDashboardDatas";

const AdminDashboard = () => {
  const { userTypes, systemInfo } = useGetDashboardDatas();

  return (
    <section className="min-h-screen w-full px-10">
      {/* Computer Infos */}
      <ul className="flex flex-wrap justify-center gap-x-10">
        <li>
          <CircleItem
            title="RAM"
            freeSpace={systemInfo?.freeMemory}
            totalSpace={systemInfo?.totalMemory}
          />
        </li>
        <li>
          <CircleItem title="CPU" percentage={systemInfo?.cpuUsage} />
        </li>
        <li>
          <CircleItem
            title="Disk"
            freeSpace={systemInfo?.freeDiskSpace}
            totalSpace={systemInfo?.totalDiskSpace}
          />
        </li>
      </ul>
      {/* User Infos */}
      <ul className="flex flex-wrap justify-center gap-10">
        {userTypes?.map(({ user_type, count }) => (
          <li key={user_type}>
            <SquareItem title={user_type} count={count} />
          </li>
        ))}
      </ul>
    </section>
  );
};

export default AdminDashboard;
