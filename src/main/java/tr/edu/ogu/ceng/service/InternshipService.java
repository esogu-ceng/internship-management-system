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
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.InternshipDto;
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

	private StudentRepository studentRepository;
	private CompanyRepository companyRepository;
	private FacultySupervisorRepository facultySupervisorRepository;
	private UserRepository userRepository;
	private FacultyRepository facultyRepository;
	private UserTypeRepository userTypeRepository;

	private ModelMapper modelMapper;

	public InternshipDto addInternship(InternshipDto internshipDto) {
		modelMapper = new ModelMapper();
		Internship internship = modelMapper.map(internshipDto, Internship.class);
		LocalDateTime dateTime = LocalDateTime.now();
		internship.setCreateDate(dateTime);
		internship.setUpdateDate(dateTime);
		internship = internshipRepository.save(internship);

		return modelMapper.map(internship, InternshipDto.class);
	}

	public InternshipDto updateInternship(InternshipDto internshipDto) {
		if (!internshipRepository.existsById(internshipDto.getId()))
			throw new EntityNotFoundException("Internship not found!");

		modelMapper = new ModelMapper();
		Internship internship = modelMapper.map(internshipDto, Internship.class);

		LocalDateTime dateTime = LocalDateTime.now();
		internship.setCreateDate(internshipRepository.getById(internship.getId()).getCreateDate());
		internship.setUpdateDate(dateTime);

		internship = internshipRepository.save(internship);
		return modelMapper.map(internship, InternshipDto.class);

	}

	public Optional<Internship> getInternship(Long id) {

		if (!internshipRepository.existsById(id)) {
			log.error("Internship not found with id {}", id);
			throw new EntityNotFoundException("Internship not found!");
		}
			
		

		return internshipRepository.findById(id);
	}

	public boolean deleteInternship(Long id) {

		if (!internshipRepository.existsById(id)) {
			log.error("Internship not found with id {}", id);
			return false;
		}
			

		internshipRepository.deleteById(id);
		log.info("Internship deleted successfully.");
		return true;
	}

	public Internship approveInternship(Long id) {

		if (!internshipRepository.existsById(id))
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException("Internship not found!");

		LocalDateTime dateTime = LocalDateTime.now();
		Internship internship = internshipRepository.findById(id).orElse(null);
		internship.setStatus(InternshipStatus.APPROVED);
		internship.setUpdateDate(dateTime);
		internshipRepository.save(internship);

		log.info("Internship Approved!");
		return internship;

	}

}
