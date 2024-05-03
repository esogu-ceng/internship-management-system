package tr.edu.ogu.ceng.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmailTest {
    @Mock
    private SettingService settingService;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getJavaMailSenderPropertiesTest() {
        when(settingService.findValueByKey("mail_host")).thenReturn("test@example.com");
        when(settingService.findValueByKey("mail_port")).thenReturn("587");
        when(settingService.findValueByKey("mail_username")).thenReturn("user@example.com");
        when(settingService.findValueByKey("mail_password")).thenReturn("pass123");

        // Burada sendEmail fonksiyonunu çağırarak içerideki getJavaMailSender'ın çalışıp çalışmadığını kontrol ediyoruz.
        emailService.sendEmail("test@example.com", "Subject", "Message");

        // JavaMailSenderImpl sınıfının ilgili setter metodları çağrıldı mı diye kontrol etmek
        verify(settingService, times(1)).findValueByKey("mail_host");
        verify(settingService, times(1)).findValueByKey("mail_port");
        verify(settingService, times(1)).findValueByKey("mail_username");
        verify(settingService, times(1)).findValueByKey("mail_password");
    }






}
