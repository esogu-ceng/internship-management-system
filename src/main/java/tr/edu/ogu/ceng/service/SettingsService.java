package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.SettingsRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Settings;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
public class SettingsService {
    @Autowired
    private SettingsRepository settingsRepository ;

    public Settings updateSettings(Settings settings){
    	if (!settingsRepository.existsById(settings.getId()))
    		throw new EntityNotFoundException("Settings not found!");
    	return settingsRepository.save(settings);
    }
    public Settings getSettings(long id) throws EntityNotFoundException {
    	Settings settings = settingsRepository.findById(id).orElse(null);
    	if (settings == null) {
    		throw new EntityNotFoundException();
    	}
    	return settings;
    }
}



