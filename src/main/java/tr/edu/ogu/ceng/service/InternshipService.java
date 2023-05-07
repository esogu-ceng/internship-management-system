package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
@Slf4j
@Service
@NoArgsConstructor
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
	private UserTypeRepository userTypeRepository;
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
		}catch (Exception e){
			log.error("Error occurred while saving internship: {}", e.getMessage());
			throw e;
		}
	}

	public InternshipDto updateInternship(InternshipRequestDto internshipDto) {
		if (!internshipRepository.existsById(internshipDto.getId())) {
			log.warn("Internship not found!");
			throw new EntityNotFoundException("Internship not found!");
		}

		modelMapper = new ModelMapper();
		Internship internship = modelMapper.map(internshipDto, Internship.class);

		LocalDateTime dateTime = LocalDateTime.now();
		internship.setCreateDate(internshipRepository.getById(internship.getId()).getCreateDate());
		internship.setUpdateDate(dateTime);

		internship = internshipRepository.save(internship);
		log.info("Internship has been updated successfully.");
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
		try{
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
		}catch (Exception e){
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

}
