package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dto.SettingDto;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
@AllArgsConstructor
@Slf4j
public class SettingService {

	@Autowired
	private SettingRepository settingRepository;
	private ModelMapper modelMapper;

	public SettingDto updateSetting(String key, SettingDto settingDto) {
		Setting setting = settingRepository.findByKey(key);
		setting.setValue(settingDto.getValue());
		setting.setUpdateDate(LocalDateTime.now());
		Setting savedSetting = settingRepository.save(setting);
		log.info("Setting updated successfully with key: {}", key);
		return modelMapper.map(savedSetting, SettingDto.class);
	}

	public SettingDto getSetting(String key) throws EntityNotFoundException {
		try {
			Setting setting = settingRepository.findByKey(key);
			if (setting == null) {
				throw new EntityNotFoundException("Setting not found with key: " + key);
			}
			log.info("Setting retrieved successfully with key: {}", key);
			return modelMapper.map(setting, SettingDto.class);
		} catch (Exception e) {
			log.error("An error occurred while getting setting with key {}: {}", key, e.getMessage());
			throw e;
		}
	}

	public String findValueByKey(String key) {
		try {
			Setting setting = settingRepository.findByKey(key);
			if (setting == null) {
				throw new EntityNotFoundException("Setting not found with key: " + key);
			}
			log.info("Value retrieved successfully for setting with key: {}", key);
			return setting.getValue();
		} catch (Exception e) {
			log.error("An error occurred while finding value for setting with key {}: {}", key, e.getMessage());
			throw e;
		}
	}

}