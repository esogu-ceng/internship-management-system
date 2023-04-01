package tr.edu.ogu.ceng;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InternshipManagementSystem {

	public static void main(String[] args) {
		SpringApplication.run(InternshipManagementSystem.class, args);
	} 
	
	@Bean // uygulama oluşur oluşmaz bir modelmapperManager için bir modelmapper nesnesi  oluşturulcak.
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
