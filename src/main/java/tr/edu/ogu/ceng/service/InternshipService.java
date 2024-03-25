package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;
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
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseCompanyDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.Student;

@Slf4j
@Service
@AllArgsConstructor
@Builder
public class InternshipService {
	@Autowired
	private InternshipRepository internshipRepository;
	private StudentRepository studentRepository;
	private CompanyRepository companyRepository;
	private FacultySupervisorRepository facultySupervisorRepository;
	private UserRepository userRepository;
	private FacultyRepository facultyRepository;
	private final ModelMapper modelMapper;
	private CompanyService companyService;
	private StudentService studentService;
	/**
	 * Adds a new internship
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
			log.info("Insternship has been added successfully.");
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
		return modelMapper.map(internship, InternshipResponseDto.class);
	}

	public CompanyDto getCompanyByInternshipId(Long id) {
		try {
			if (!internshipRepository.existsById(id)) {
				log.warn("Internship not found!");
				throw new EntityNotFoundException("Internship not found!");
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
			return modelMapper.map(internshipOptional.orElseThrow(), CompanyDto.class);
		} catch (Exception e) {
			log.error("Error occured while getting the Company by InternshipId", id);
			throw new EntityNotFoundException("Error occured while getting the Company by InternshipId!");
		}
	}

	public boolean deleteInternship(Long id) {
		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			return false;
		}

		internshipRepository.deleteById(id);
		log.info("Intership has been deleted successfully.");
		return true;
	}

	public InternshipStatus chanceInternshipStatus(Long id, InternshipStatus status) {

		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException("Internship not found!");
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
			log.info("Getting all internships by student id: {} with pageable: {}", studentId, pageable);
			Page<Internship> internships = internshipRepository.findAllByStudentId(studentId, pageable);
			if (internships.isEmpty()) {
				log.warn("The internship list is empty.");
			}
			Page<InternshipResponseDto> internshipDtos = internships
					.map(internship -> modelMapper.map(internship, InternshipResponseDto.class));
			return internshipDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internships: {}", e.getMessage());
			throw e;
		}
	}

	public Page<InternshipResponseDto> getAllInternshipsByCompanyId(Long companyId, Pageable pageable) {
		try {
			log.info("Getting all internships by company id: {} with pageable: {}", companyId, pageable);
			Page<Internship> internships = internshipRepository.findAllByCompanyId(companyId, pageable);
			if (internships.isEmpty()) {
				log.warn("The internship list is empty.");
			}
			Page<InternshipResponseDto> internshipDtos = internships
					.map(internship -> modelMapper.map(internship, InternshipResponseDto.class));
			return internshipDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internships: {}", e.getMessage());
			throw e;
		}
	}

	public StudentResponseDto getStudentByInternshipId(Long id) {
		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException("Internship not found!");
		}

		Internship internship = internshipRepository.findById(id).orElse(null);
		Student student = internship.getStudent();

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
			return internshipDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internships: {}", e.getMessage());
			throw e;
		}
	}

	public long countApprovedInternships() {
		return internshipRepository.countByStatus(InternshipStatus.APPROVED);
	}

	public long countRejectedInternships() {
		return internshipRepository.countByStatus(InternshipStatus.REJECTED);
	}

	public long countPendingInternships() {
		return internshipRepository.countByStatus(InternshipStatus.PENDING);
	}
	public long countDistinctStudents() {
	    return internshipRepository.countDistinctStudents();
	}
	
	public Long countAllInternships() {
		return internshipRepository.count();
	}


}
