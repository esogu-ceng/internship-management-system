package tr.edu.ogu.ceng.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.ceng.service.AdminService;
import tr.edu.ogu.ceng.util.SystemInfo;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @GetMapping("/systemInfo")
    public SystemInfo getSystemInfo() {
        return adminService.getSystemInfo();
    }
}
