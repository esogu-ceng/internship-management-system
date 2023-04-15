package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
public class SettingService {
    @Autowired
    private SettingRepository settingRepository;

    public Setting updateSetting(Setting setting){
    	if (!settingRepository.existsById(setting.getId()))
    		throw new EntityNotFoundException("Setting not found!");
    	return settingRepository.save(setting);
    }

    public Setting getSetting(String key) throws EntityNotFoundException {
    	Setting setting = settingRepository.findByKey(key);
    	if (setting == null) {
    		throw new EntityNotFoundException();
    	}
    	return setting;
    }
    
    public String findValueByKey(String key) {
    	Setting setting = settingRepository.findByKey(key);
    	if (setting == null) {
    		throw new EntityNotFoundException("Setting with "+ key +" not found!");
    	}
    	return setting.getValue();
    }
}