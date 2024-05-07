package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dto.SettingDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

public class SettingServiceTest {

    @Mock
    private SettingRepository settingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MessageResource messageResource;

    private SettingService settingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        settingService = new SettingService(settingRepository, modelMapper, messageResource);
    }


    @Test
    public void testUpdateSetting() {
        String key = "testKey";
        String value = "testValue";

        SettingDto settingDto = new SettingDto();
        settingDto.setKey(key);
        settingDto.setValue(value);

        Setting setting = new Setting();
        setting.setKey(key);
        setting.setValue(value);
        setting.setUpdateDate(LocalDateTime.now());

        when(settingRepository.findByKey(key)).thenReturn(setting);
        when(settingRepository.save(any(Setting.class))).thenReturn(setting);
        when(modelMapper.map(setting, SettingDto.class)).thenReturn(settingDto);

        SettingDto updatedSetting = settingService.updateSetting(key, settingDto);

        assertNotNull(updatedSetting);
        assertEquals(key, updatedSetting.getKey());
        assertEquals(value, updatedSetting.getValue());
    }

    @Test
    public void testGetSetting_NotFound() {
        String key = "nonExistentKey";

        when(settingRepository.findByKey(key)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            settingService.getSetting(key);
        });
    }

    @Test
    public void testFindValueByKey_SettingFound() {
        String key = "testKey";
        String value = "testValue";

        Setting setting = new Setting();
        setting.setKey(key);
        setting.setValue(value);

        when(settingRepository.findByKey(key)).thenReturn(setting);

        String result = settingService.findValueByKey(key);

        assertEquals(value, result);
    }

    @Test
    public void testFindValueByKey_SettingNotFound() {
        String key = "nonExistentKey";
    
        when(settingRepository.findByKey(key)).thenReturn(null);
        
        assertThrows(EntityNotFoundException.class, () -> {
            settingService.findValueByKey(key);
        });
    }

    @Test
public void testGetSetting_SettingFound() throws EntityNotFoundException {
    String key = "testKey";
    String value = "testValue";

    Setting setting = new Setting();
    setting.setKey(key);
    setting.setValue(value);

    when(settingRepository.findByKey(key)).thenReturn(setting);
    when(modelMapper.map(setting, SettingDto.class)).thenReturn(new SettingDto(key, value));

    SettingDto result = settingService.getSetting(key);

    assertEquals(key, result.getKey());
    assertEquals(value, result.getValue());
}

    
}
