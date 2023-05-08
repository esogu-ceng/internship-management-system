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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.model.Internship;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipService {
	@Autowired
	private InternshipRepository internshipRepository;
	private ModelMapper modelMapper;
	private CompanyService companyService;
	private StudentService studentService;

	public InternshipResponseDto addInternship(InternshipRequestDto internshipDto) {
		modelMapper = new ModelMapper();
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

	public InternshipDto updateInternship(InternshipDto internshipDto) {
		Internship internship = internshipRepository.findById(internshipDto.getId()).orElse(null);
		if (internship == null) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
		modelMapper = new ModelMapper();
		Internship internshipMapped = modelMapper.map(internshipDto, Internship.class);

		LocalDateTime dateTime = LocalDateTime.now();
		internshipMapped.setCreateDate(internship.getCreateDate());
		internshipMapped.setUpdateDate(dateTime);

		internship = internshipRepository.save(internshipMapped);
		log.info("Internship has been updated successfully with id = {}.", internship.getId());
		return modelMapper.map(internship, InternshipDto.class);
	}

	public Optional<Internship> getInternship(Long id) {
		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found!");
			throw new EntityNotFoundException("Internship not found!");
		}

		return internshipRepository.findById(id);
	}

	public CompanyDto getCompanyByInternshipId(Long id) {
		ModelMapper modelMapper = new ModelMapper();
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

	public Internship approveInternship(Long id) {

		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException("Internship not found!");
		}

		LocalDateTime dateTime = LocalDateTime.now();
		Internship internship = internshipRepository.findById(id).orElse(null);
		internship.setStatus(InternshipStatus.APPROVED);
		internship.setUpdateDate(dateTime);
		internshipRepository.save(internship);

		log.info("Internship Approved!");
		return internship;

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
  
  public StudentResponseDto getStudentByInternshipId(Long id){
		if (!internshipRepository.existsById(id)) {
			log.warn("Internship not found with id {}", id);
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException("Internship not found!");
		}

		Internship internship = internshipRepository.findById(id).orElse(null);
		Student student = internship.getStudent();

		modelMapper = new ModelMapper();
		return modelMapper.map(student, StudentResponseDto.class);
    }

}
