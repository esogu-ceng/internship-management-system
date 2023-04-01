package tr.edu.ogu.ceng.service.concretes;


import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.CompanySupervisorDao;
import tr.edu.ogu.ceng.dto.registerAsCompanySupervisor.RegisterAsCompanySupervisorRequest;
import tr.edu.ogu.ceng.dto.registerAsCompanySupervisor.RegisterAsCompanySupervisorResponse;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.service.abstracts.RegisterAsCompanySupervisorService;

@Service
@AllArgsConstructor
public class RegisterAsCompanySupervisorManager implements RegisterAsCompanySupervisorService{

	CompanySupervisorDao companySupervisorDao;
	ModelMapper modelMapper;
	@Override
	public RegisterAsCompanySupervisorResponse register(RegisterAsCompanySupervisorRequest request) {

		checkIfPasswordsMatchingValidation(request);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD).setAmbiguityIgnored(true);
		CompanySupervisor companySupervisor = modelMapper.map(request, CompanySupervisor.class);
		companySupervisor.setId(0);
		companySupervisor.setCreateDate(new Timestamp(System.currentTimeMillis()));
		companySupervisorDao.save(companySupervisor);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE).setAmbiguityIgnored(true);
		RegisterAsCompanySupervisorResponse response = modelMapper.map(companySupervisor, RegisterAsCompanySupervisorResponse.class);
		
		return response;
	}

	private void checkIfPasswordsMatchingValidation(RegisterAsCompanySupervisorRequest registerAsCompanySupervisorRequest) {
		if(!registerAsCompanySupervisorRequest.getPassword().toString().equals(registerAsCompanySupervisorRequest.getConfirmPassword().toString())) 
			throw new RuntimeException("Şifreler uyuşmuyor!");
	}

}
