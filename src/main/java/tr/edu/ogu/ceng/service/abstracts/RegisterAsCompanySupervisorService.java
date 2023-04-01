package tr.edu.ogu.ceng.service.abstracts;

import tr.edu.ogu.ceng.dto.registerAsCompanySupervisor.RegisterAsCompanySupervisorRequest;
import tr.edu.ogu.ceng.dto.registerAsCompanySupervisor.RegisterAsCompanySupervisorResponse;

public interface RegisterAsCompanySupervisorService {

	RegisterAsCompanySupervisorResponse register(RegisterAsCompanySupervisorRequest request);
}
