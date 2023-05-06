package tr.edu.ogu.ceng.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.service.Exception.ServiceException;

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

	@Before("execution(* tr.edu.ogu.ceng.service.InternshipService.updateInternship(..)) && args(internshipDto) || execution(* tr.edu.ogu.ceng.service.InternshipService.addInternship(..)) && args(internshipDto)")
	public void beforeAddAndUpdateInternship(InternshipDto internshipDto) {
		boolean existingFacultySupervisorId = facultySupervisorRepository
				.existsById(internshipDto.getFacultySupervisorId());
		boolean existingStudentId = studentRepository.existsById(internshipDto.getStudentId());
		boolean existingCompanyId = companyRepository.existsById(internshipDto.getCompanyId());

		if (!existingFacultySupervisorId) {
			throw new ServiceException("Fakülte sorumlusu bulunamadı!"); // TODO - EntityNotFoundException with message.
		} else if (!existingStudentId) {
			throw new ServiceException("Öğrenci bulunamadı!"); // TODO - EntityNotFoundException with message.
		} else if (!existingCompanyId) {
			throw new ServiceException("Şirket bulunamadı!"); // TODO - EntityNotFoundException with message.
		} else if (internshipDto.getDays() < 0) {
			throw new ServiceException("Staj gün sayısı negatif olamaz!");
		} else if (internshipDto.getStartDate().compareTo(internshipDto.getEndDate()) > 0) {
			throw new ServiceException("Staj bitiş tarihi başlangıç tarihinden önce olamaz!");
		}

	}

}
