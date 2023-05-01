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
import tr.edu.ogu.ceng.dto.InternshipRegistryDto;
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

	public InternshipRegistryDto updateInternshipRegistry(InternshipRegistryDto internshipRegistryDto) {
		ModelMapper modelMapper = new ModelMapper();
		InternshipRegistry internshipRegistry = modelMapper.map(internshipRegistryDto, InternshipRegistry.class);
		if (!internshipRegistryRepository.existsById(internshipRegistry.getId())) {
			log.warn("Internship registry with ID {} not found", internshipRegistry.getId());
			throw new EntityNotFoundException("Internship Registry not found!");
		}
		internshipRegistry.setCreateDate(internshipRegistryRepository.getById(internshipRegistry.getId()).getCreateDate());
		internshipRegistry.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		try {
			internshipRegistry = internshipRegistryRepository.save(internshipRegistry);
            log.info("Internship registry updated: {}", internshipRegistry);
        } catch (Exception e) {
            log.error("Error occurred while updating internship registry: {}", e.getMessage());
            throw e;
        }

		LocalDateTime dateTime = LocalDateTime.now();
		internshipRegistry.setCreateDate(internshipRegistryRepository.getById(internshipRegistry.getId()).getCreateDate());
		internshipRegistry.setUpdateDate(dateTime);

		internshipRegistry = internshipRegistryRepository.save(internshipRegistry);
		return modelMapper.map(internshipRegistry, InternshipRegistryDto.class);
	}

	public InternshipRegistryDto addInternshipRegistry(InternshipRegistryDto internshipRegistryDto) {
		ModelMapper modelMapper = new ModelMapper();
		InternshipRegistry internshipRegistry = modelMapper.map(internshipRegistryDto, InternshipRegistry.class);
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internshipRegistry.setCreateDate(localDateTime);
		internshipRegistry.setUpdateDate(localDateTime);
		
		try {
			internshipRegistry = internshipRegistryRepository.save(internshipRegistry);
            log.info("Internship registry saved: {}", internshipRegistry);
        } catch (Exception e) {
            log.error("Error occurred while saving internship registry: {}", e.getMessage());
            throw e;
        }

		internshipRegistry = internshipRegistryRepository.save(internshipRegistry);
		LocalDateTime dateTime = LocalDateTime.now();
		internshipRegistry.setCreateDate(dateTime);
		internshipRegistry.setUpdateDate(dateTime);
		InternshipRegistry addedInternshipRegistry = internshipRegistryRepository.save(internshipRegistry);
		return modelMapper.map(addedInternshipRegistry, InternshipRegistryDto.class);
	}

	public Page<InternshipRegistryDto> getAllInternshipRegistiries(Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting all internship registriries with pageable: {}", pageable);
			Page<InternshipRegistry> internshipRegistries = internshipRegistryRepository.findAll(pageable);
			if (internshipRegistries.isEmpty()) {
				log.warn("The internship registry list is empty.");
			}
			Page<InternshipRegistryDto> internshipRegistryDtos = internshipRegistries
					.map(internshipRegistry -> modelMapper.map(internshipRegistry, InternshipRegistryDto.class));
			return internshipRegistryDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internship registries: {}", e.getMessage());
			throw e;
		}
	}

}
