package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

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
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.requests.InternshipRegistryRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipRegistryResponseDto;
import tr.edu.ogu.ceng.model.InternshipRegistry;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InternshipRegistryService {

	@Autowired
	private InternshipRegistryRepository internshipRegistryRepository;
	private InternshipRegistryService internshipRegistryService;
	private InternshipRepository internshipRepository;
	private StudentRepository studentRepository;
	private CompanyRepository companyRepository;
	private FacultySupervisorRepository facultySupervisorRepository;
	private UserRepository userRepository;
	private FacultyRepository facultyRepository;
	private UserTypeRepository userTypeRepository;
	private ModelMapper modelMapper;

	public boolean deleteInternshipRegistry(long id) {
		if (!internshipRegistryRepository.existsById(id)) {
			log.warn("Internship registry with ID {} not found", id);
			return false;
		}
		internshipRegistryRepository.deleteById(id);
		log.info("Internship registry deleted with ID: {}", id);
		return true;
	}

	public InternshipRegistryResponseDto updateInternshipRegistry(InternshipRegistryRequestDto requestDto) {
		ModelMapper modelMapper = new ModelMapper();
		InternshipRegistry internshipRegistry = modelMapper.map(requestDto, InternshipRegistry.class);
		if (!internshipRegistryRepository.existsById(internshipRegistry.getId())) {
			log.warn("Internship registry with ID {} not found", internshipRegistry.getId());
			throw new EntityNotFoundException("Internship Registry not found!");
		}

		try {
			LocalDateTime dateTime = LocalDateTime.now();
			internshipRegistry
					.setCreateDate(internshipRegistryRepository.getById(internshipRegistry.getId()).getCreateDate());
			internshipRegistry.setUpdateDate(dateTime);
			InternshipRegistry updatedInternshipRegistry = internshipRegistryRepository.save(internshipRegistry);
			log.info("Internship registry updated: {}", updatedInternshipRegistry);
			return modelMapper.map(updatedInternshipRegistry, InternshipRegistryResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while updating internship registry: {}", e.getMessage());
			throw e;
		}
	}

	public InternshipRegistryResponseDto addInternshipRegistry(InternshipRegistryRequestDto internshipRegistryDto) {
		ModelMapper modelMapper = new ModelMapper();
		InternshipRegistry internshipRegistry = modelMapper.map(internshipRegistryDto, InternshipRegistry.class);
		InternshipRegistry addedInternshipRegistry = new InternshipRegistry();

		try {
			LocalDateTime dateTime = LocalDateTime.now();
			internshipRegistry.setCreateDate(dateTime);
			internshipRegistry.setUpdateDate(dateTime);
			addedInternshipRegistry = internshipRegistryRepository.save(internshipRegistry);
			log.info("Internship registry saved: {}", internshipRegistry);
			return modelMapper.map(addedInternshipRegistry, InternshipRegistryResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while saving internship registry: {}", e.getMessage());
			throw e;
		}
	}

	public Page<InternshipRegistryResponseDto> getAllInternshipRegistiries(Long internshipId, Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting internship registriries by id: {} with pageable: {}", internshipId, pageable);
			Page<InternshipRegistry> internshipRegistries = internshipRegistryRepository.findAllById(internshipId, pageable);
			if (internshipRegistries.isEmpty()) {
				log.warn("The internship registry list is empty.");
			}
			Page<InternshipRegistryResponseDto> internshipRegistryDtos = internshipRegistries.map(
					internshipRegistry -> modelMapper.map(internshipRegistry, InternshipRegistryResponseDto.class));
			return internshipRegistryDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internship registries: {}", e.getMessage());
			throw e;
		}
	}

	public InternshipRegistryResponseDto getInternshipRegistry(Long id) {
		if (!internshipRegistryRepository.existsById(id)) {
			log.warn("Internship registry with ID {} not found", id);
			throw new EntityNotFoundException("Internship Registry not found!");
		}
		try {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(internshipRegistryRepository.getById(id), InternshipRegistryResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while getting internship registry: {}", e.getMessage());
			throw e;
		}
	}

}
