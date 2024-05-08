package tr.edu.ogu.ceng.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfo {
    private double totalMemory;
    private double freeMemory;
    private double totalDiskSpace;
    private double freeDiskSpace;
    private double cpuUsage;
}