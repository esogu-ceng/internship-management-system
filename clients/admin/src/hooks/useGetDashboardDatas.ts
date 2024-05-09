import { useEffect, useState } from "react";
import { UserTypeAndCount } from "../types/UserTypeAndCount";
import { toast } from "react-toastify";
import { SystemInfo } from "../types/SystemInfo";

const useGetDashboardDatas = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const [userTypes, setUserTypes] = useState<UserTypeAndCount[] | null>(null);
  const [systemInfo, setSystemInfo] = useState<SystemInfo | null>(null);

  const fetchUserTypes = async () => {
    try {
      setLoading(true);
      // const response = await fetch(
      //   `${process.env.REACT_APP_API_BASE_URI}api/user/userCounts`
      // );
      const response = await fetch(`/api/user/userCounts`);
      const data = await response.json();

      setUserTypes(data);
      setLoading(false);
    } catch (error: any) {
      console.log(error.message);
      toast.error("Kullanıcı türleri alınırken bir hata oluştu.");
    }
  };

  const fetchSystemInfo = async () => {
    try {
      const response = await fetch(`/api/admin/systemInfo`);
      const data = await response.json();

      setSystemInfo(data);
    } catch (error: any) {
      console.log(error.message);
      toast.error("Sistem bilgileri alınırken bir hata oluştu.");
    }
  };

  useEffect(() => {
    fetchUserTypes();
    // fetch system info every 5 second
    const interval = setInterval(() => {
      fetchSystemInfo();
    }, 5000);
    return () => clearInterval(interval);
  }, []);

  return { loading, userTypes, systemInfo };
};

export default useGetDashboardDatas;
