package tr.edu.ogu.ceng.controller;
import javax.persistence.EntityNotFoundException;

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
	    try {
	        Settings updatedSettings = settingsService.updateSettings(settings);
	        return ResponseEntity.ok(updatedSettings);
	    } 
	    catch (EntityNotFoundException ex) {
	        return ResponseEntity.notFound().build();
	    } 
	    catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/{key}")
	public ResponseEntity<Settings> getSettings(@PathVariable(name = "key") String key) throws EntityNotFoundException {
	    Settings settings = settingsService.getSettings(key);
	    return ResponseEntity.ok(settings);
	}
}
