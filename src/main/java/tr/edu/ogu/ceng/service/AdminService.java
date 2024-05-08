package tr.edu.ogu.ceng.service;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import oshi.hardware.HardwareAbstractionLayer;
import tr.edu.ogu.ceng.util.SystemInfo;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;


@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class AdminService {

    private long[] prevTicks;

    public SystemInfo getSystemInfo() {
        SystemInfo info = new SystemInfo();
        HardwareAbstractionLayer hal = new oshi.SystemInfo().getHardware();

        // Get memory info
        GlobalMemory memory = hal.getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();

        // Convert the values from bytes to gigabytes
        double totalMemoryInGB = (double) totalMemory / (1024 * 1024 * 1024);
        double availableMemoryInGB = (double) availableMemory / (1024 * 1024 * 1024);

        info.setTotalMemory(totalMemoryInGB);
        info.setFreeMemory(availableMemoryInGB);

        // Get disk info
        long totalDiskSpace = 0;
        long freeDiskSpace = 0;

        for (FileStore store : FileSystems.getDefault().getFileStores()) {
            try {
                totalDiskSpace += store.getTotalSpace();
                freeDiskSpace += store.getUsableSpace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Convert the values from bytes to gigabytes
        double totalDiskSpaceInGB = (double) totalDiskSpace / (1024 * 1024 * 1024);
        double usableDiskSpaceInGB = (double) freeDiskSpace / (1024 * 1024 * 1024);

        info.setTotalDiskSpace(totalDiskSpaceInGB);
        info.setFreeDiskSpace(usableDiskSpaceInGB);

        // Get CPU usage
        CentralProcessor processor = hal.getProcessor();
        long[] ticks = processor.getSystemCpuLoadTicks();
        double cpuLoad = prevTicks == null ? 0 : processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = ticks;

        info.setCpuUsage(cpuLoad);

        return info;
    }

}