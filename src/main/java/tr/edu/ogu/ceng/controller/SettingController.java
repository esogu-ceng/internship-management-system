package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.SettingDto;
import tr.edu.ogu.ceng.service.SettingService;

@RestController
@RequestMapping("/api/setting")
public class SettingController {
	@Autowired
	private SettingService settingService;

	@PutMapping

	public ResponseEntity<SettingDto> updateSetting(@RequestBody SettingDto setting) {
		SettingDto updatedSetting = settingService.updateSetting(setting);
		return ResponseEntity.ok(updatedSetting);
	}

	@GetMapping("/{key}")
	public ResponseEntity<SettingDto> getSetting(@PathVariable(name = "key") String key) {
		SettingDto setting = settingService.getSetting(key);

		return ResponseEntity.ok(setting);
	}
}
