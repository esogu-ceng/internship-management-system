package tr.edu.ogu.ceng.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.ceng.model.Settings;
import tr.edu.ogu.ceng.service.SettingsService;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
	@Autowired

	SettingsService settingsService;

	@PutMapping
	public ResponseEntity<Settings> updateSettings(@RequestBody Settings settings) {
		Settings newSettings = settingsService.updateSettings(settings);
		if(newSettings!=null ) {
			return ResponseEntity.ok(newSettings);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@GetMapping("/{key}")
	public ResponseEntity<Settings> getSettings(@PathVariable(name = "key") String key) throws Exception {
		Settings settings = settingsService.getSettings(key);
		if(settings!=null) {
			return ResponseEntity.ok(settings);
		}
		return ResponseEntity.notFound().build();
	}
}
