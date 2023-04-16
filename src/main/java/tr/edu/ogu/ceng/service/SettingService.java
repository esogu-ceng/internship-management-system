package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Slf4j
@Service
public class SettingService {
	@Autowired
	private SettingRepository settingRepository;

	public Setting updateSetting(Setting setting) {
		if (!settingRepository.existsById(setting.getId())) {
			log.warn("Setting with id {} not found!", setting.getId());
			throw new EntityNotFoundException("Setting not found!");
		}
		log.info("Updating setting with id: {}", setting.getId());
		return settingRepository.save(setting);
	}

	public Setting getSetting(String key) throws EntityNotFoundException {
		Setting setting = settingRepository.findByKey(key);
		if (setting == null) {
			log.warn("Setting with key {} not found!", key);
			throw new EntityNotFoundException();
		}
		log.info("Setting with key {} found!", key);
		return setting;
	}

	public String findValueByKey(String key) {
		Setting setting = settingRepository.findByKey(key);
		if (setting == null) {
			log.warn("Setting with key {} not found!", key);
			throw new EntityNotFoundException();
		}
		log.info("Found value {} for key: {}", setting.getValue(), key);
		return setting.getValue();
	}

}