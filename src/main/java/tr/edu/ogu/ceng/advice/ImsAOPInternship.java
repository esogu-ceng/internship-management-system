package tr.edu.ogu.ceng.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.service.Exception.ServiceException;

import java.util.Locale;

@Slf4j
@Aspect
@Component
public class ImsAOPInternship {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private FacultySupervisorRepository facultySupervisorRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private MessageResource messageResource;

	@Before("execution(* tr.edu.ogu.ceng.service.InternshipService.updateInternship(..)) && args(internshipDto) || execution(* tr.edu.ogu.ceng.service.InternshipService.addInternship(..)) && args(internshipDto)")
	public void beforeAddAndUpdateInternship(InternshipRequestDto internshipDto) {
		boolean existingFacultySupervisorId = facultySupervisorRepository
				.existsById(internshipDto.getFacultySupervisorId());
		boolean existingStudentId = studentRepository.existsById(internshipDto.getStudentId());
		boolean existingCompanyId = companyRepository.existsById(internshipDto.getCompanyId());

		if (!existingFacultySupervisorId) {
			throw new ServiceException(messageResource.getMessage("facultySupervisorNotFound"));
		} else if (!existingStudentId) {
			throw new ServiceException(messageResource.getMessage("studentNotFound"));
		} else if (!existingCompanyId) {
			throw new ServiceException(messageResource.getMessage("companyNotFound"));
		} else if (internshipDto.getDays() < 0) {
			throw new ServiceException(messageResource.getMessage("negativeDays"));
		} else if (internshipDto.getStartDate().compareTo(internshipDto.getEndDate()) > 0) {
			throw new ServiceException(messageResource.getMessage("invalidDates"));
		}

	}

}
