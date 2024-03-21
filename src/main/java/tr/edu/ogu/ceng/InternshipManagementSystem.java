package tr.edu.ogu.ceng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.service.EmailService;
import tr.edu.ogu.ceng.service.PasswordGeneratorService;
import tr.edu.ogu.ceng.service.UserService;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class InternshipManagementSystem {

	public static void main(String[] args) {

		SpringApplication.run(InternshipManagementSystem.class, args);



	}

}