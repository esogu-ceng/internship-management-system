package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dto.SettingDto;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
public class SettingService {

	@Autowired
	private SettingRepository settingRepository;
	private ModelMapper modelMapper;

	public SettingDto updateSetting(SettingDto settingDto) {
		Setting setting = modelMapper.map(settingDto, Setting.class);
		setting.setCreateDate(setting.getCreateDate());
		setting.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		if (!settingRepository.existsById(setting.getId()))
			throw new EntityNotFoundException("Setting not found!");
		return modelMapper.map(settingRepository.save(setting), SettingDto.class);

	}

	public SettingDto getSetting(String key) throws EntityNotFoundException {
		Setting setting = settingRepository.findByKey(key);
		if (setting == null) {
			throw new EntityNotFoundException();
		}
		return modelMapper.map(setting, SettingDto.class);
	}

	public String findValueByKey(String key) {
		Setting setting = settingRepository.findByKey(key);
		if (setting == null) {
			throw new EntityNotFoundException("Setting with " + key + " not found!");
		}
		return setting.getValue();
	}

}