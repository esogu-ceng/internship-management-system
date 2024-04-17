package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanySupervisorRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseCompanyDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.security.AuthService;

@Slf4j
@Service
@AllArgsConstructor
@Builder
public class InternshipService {
	@Autowired
	private InternshipRepository internshipRepository;
	private StudentRepository studentRepository;
	private CompanySupervisorRepository companySupervisorRepository;
	private FacultySupervisorRepository facultySupervisorRepository;
	private UserRepository userRepository;
	private FacultyRepository facultyRepository;
	private final ModelMapper modelMapper;
	private MessageResource messageResource;
	private AuthService authService;

	/**
	 * Adds a new internship
	 * 
	 * @param internshipDto
	 * @return InternshipResponseDto
	 * @throws Exception
	 */
	public InternshipResponseDto addInternship(InternshipRequestDto internshipDto) {
		Internship internship = modelMapper.map(internshipDto, Internship.class);
		try {
			LocalDateTime dateTime = LocalDateTime.now();
			internship.setCreateDate(dateTime);
			internship.setUpdateDate(dateTime);
			internship = internshipRepository.save(internship);

			log.info("Internship has been saved successfully with id = {}.", internship.getId());
			return modelMapper.map(internship, InternshipResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while saving internship: {}", e.getMessage());
			throw e;
		}
	}

	public InternshipResponseDto updateInternship(InternshipRequestDto internshipDto) {
		Internship internship = internshipRepository.findById(internshipDto.getId()).orElse(null);
		if (internship == null) {
			log.warn("Internship not found with id {}!", internshipDto.getId());
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
		Internship internshipMapped = modelMapper.map(internshipDto, Internship.class);

		LocalDateTime dateTime = LocalDateTime.now();
		internshipMapped.setCreateDate(internship.getCreateDate());
		internshipMapped.setUpdateDate(dateTime);

		internship = internshipRepository.save(internshipMapped);
		log.info("Internship has been updated successfully with id = {}.", internship.getId());
		return modelMapper.map(internship, InternshipResponseDto.class);
	}

	public InternshipResponseDto getInternship(Long id) {
		Internship internship = internshipRepository.findById(id).orElse(null);
		if (internship == null) {
			log.warn("Internship not found with id {}!", id);
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
		log.info("Internship has been found with id = {}.", id);
		return modelMapper.map(internship, InternshipResponseDto.class);
	}

	public CompanyDto getCompanyByInternshipId(Long id) {
		try {
			if (!internshipRepository.existsById(id)) {
				log.warn("Internship not found with id {}", id);
				throw new EntityNotFoundException(messageResource.getMessage("internshipRegistryNotFound"));
			}

			Optional<Internship> internshipOptional = internshipRepository.findById(id);
			internshipOptional.ifPresent(internship -> {
				modelMapper.typeMap(Internship.class, CompanyDto.class)
						.addMapping(src -> src.getCompany().getName(), CompanyDto::setName)
						.addMapping(src -> src.getCompany().getAddress(), CompanyDto::setAddress)
						.addMapping(src -> src.getCompany().getPhoneNumber(), CompanyDto::setPhoneNumber)
						.addMapping(src -> src.getCompany().getFaxNumber(), CompanyDto::setFaxNumber)
						.addMapping(src -> src.getCompany().getEmail(), CompanyDto::setEmail)
						.addMapping(src -> src.getCompany().getScope(), CompanyDto::setScope)
						.addMapping(src -> src.getCompany().getDescription(), CompanyDto::setDescription);
			});
			log.info("Company has been found by InternshipId: {}", id);
			return modelMapper.map(internshipOptional.orElseThrow(), CompanyDto.class);
		} catch (Exception e) {
			log.error("Error occured while getting the Company by InternshipId", id);
			throw new EntityNotFoundException(messageResource.getMessage("error.getting.company.with.internshipId", id));
		}
	}

	public boolean deleteInternship(Long id) {
		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			return false;
		}

		internshipRepository.deleteById(id);
		log.info("Internship has been deleted with id = {}.", id);
		return true;
	}

	public InternshipStatus chanceInternshipStatus(Long id, InternshipStatus status) {

		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			throw new EntityNotFoundException(messageResource.getMessage("internshipRegistryNotFound"));
		}

		LocalDateTime dateTime = LocalDateTime.now();
		Internship internship = internshipRepository.findById(id).orElse(null);
		internship.setUpdateDate(dateTime);
		internship.setStatus(status);
		internshipRepository.save(internship);
		log.info("Internship with id {} {}!", id, status);

		return status;

	}

	public Page<InternshipResponseDto> getAllInternshipsByStudentId(Long studentId, Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Page<Internship> internships = internshipRepository.findAllByStudentId(studentId, pageable);
			if (internships.isEmpty()) {
				log.warn("The internship list is empty.");
			}
			Page<InternshipResponseDto> internshipDtos = internships
					.map(internship -> modelMapper.map(internship, InternshipResponseDto.class));
			log.info("Internships has been found by student id: {}", studentId);
			return internshipDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internships: {}", e.getMessage());
			throw e;
		}
	}

	public Page<InternshipResponseDto> getAllInternshipsByCompanyId(Long companyId, Pageable pageable) {
		try {
			Page<Internship> internships = internshipRepository.findAllByCompanyId(companyId, pageable);
			if (internships.isEmpty()) {
				log.warn("The internship list is empty.");
			}
			Page<InternshipResponseDto> internshipDtos = internships
					.map(internship -> modelMapper.map(internship, InternshipResponseDto.class));
			log.info("Internships has been found by company id: {}", companyId);
			return internshipDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internships: {}", e.getMessage());
			throw e;
		}
	}

	public StudentResponseDto getStudentByInternshipId(Long id) {
		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			throw new EntityNotFoundException(messageResource.getMessage("internshipRegistryNotFound"));
		}

		Internship internship = internshipRepository.findById(id).orElse(null);
		Student student = internship.getStudent();
		log.info("Student has been found by InternshipId: {}", id);
		return modelMapper.map(student, StudentResponseDto.class);
	}

	public Page<InternshipResponseCompanyDto> getAllInternshipsByFacultySupervisorId(Long faculty_supervisor_id,
			Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting all internships by faculty supervisor id: {} with pageable: {}", faculty_supervisor_id,
					pageable);
			Page<Internship> internships = internshipRepository.findAllByFacultySupervisorId(faculty_supervisor_id,
					pageable);
			if (internships.isEmpty()) {
				log.warn("The internship list is empty.");
			}
			Page<InternshipResponseCompanyDto> internshipDtos = internships
					.map(internship -> modelMapper.map(internship, InternshipResponseCompanyDto.class));
			log.info("Internships has been found by faculty supervisor id: {}", faculty_supervisor_id);
			return internshipDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internships: {}", e.getMessage());
			throw e;
		}
	}

	public long countApprovedInternships() {
		log.info("Counting approved internships");
		return internshipRepository.countByStatus(InternshipStatus.APPROVED);
	}

	public long countRejectedInternships() {
		log.info("Counting rejected internships");
		return internshipRepository.countByStatus(InternshipStatus.REJECTED);
	}

	public long countPendingInternships() {
		log.info("Counting pending internships");
		return internshipRepository.countByStatus(InternshipStatus.PENDING);
	}

	public long countDistinctStudents() {
		log.info("Counting distinct students");
		return internshipRepository.countDistinctStudents();
	}

	public Long countAllInternships() {
		log.info("Counting all internships");
		return internshipRepository.count();
	}

	public List<Object[]> countInternshipsByYear() {
		return internshipRepository.countInternshipsByYear();
	}

	public List<Object[]> countInternshipsByMonth() {
		return internshipRepository.countInternshipsByMonth();
	}

	public Page<InternshipResponseDto> getAllInternshipsCompany(Pageable pageable) {
		try {
			User authUser = authService.getAuthUser();
			CompanySupervisor companySupervisor = companySupervisorRepository.findCompanySupervisorByUserId(authUser.getId());
			Page<Internship> internships = internshipRepository.findAllByCompanyId(companySupervisor.getCompany().getId(), pageable);
			if (internships.isEmpty()) {
				log.warn("The internship list is empty.");
			}
			Page<InternshipResponseDto> internshipDtos = internships
					.map(internship -> modelMapper.map(internship, InternshipResponseDto.class));
			log.info("Internships has been found by company id: {}", companySupervisor.getCompany().getId());
			return internshipDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internships: {}", e.getMessage());
			throw e;
		}
	}

}
