package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.service.SettingService;

@RestController
@RequestMapping("/api/setting")
public class SettingController {
	@Autowired
	private SettingService settingService;

	@PutMapping
	public ResponseEntity<Setting> updateSetting(@RequestBody Setting setting) {
	    Setting updatedSetting = settingService.updateSetting(setting);
	    return ResponseEntity.ok(updatedSetting);
	}

	@GetMapping("/{key}")
	public ResponseEntity<Setting> getSetting(@PathVariable(name = "key") String key) {
		Setting setting = settingService.getSetting(key);
		return ResponseEntity.ok(setting);
	}
}
