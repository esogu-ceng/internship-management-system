package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.dto.InternshipRegistryDto;
import tr.edu.ogu.ceng.model.InternshipRegistry;
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class InternshipRegistryService {

	@Autowired
	private InternshipRegistryRepository internshipRegistryRepository;
	private ModelMapper modelMapper;

	public boolean deleteInternshipRegistry(long id) {
		if (!internshipRegistryRepository.existsById(id)) {
			log.warn("Internship registry with ID {} not found", id);
			return false;
		}
		internshipRegistryRepository.deleteById(id);
		log.info("Internship registry deleted with id: {}", id);
		return true;
	}

	public InternshipRegistryDto updateInternshipRegistry(InternshipRegistryDto internshipRegistryDto) {
		InternshipRegistry internshipRegistry = modelMapper.map(internshipRegistryDto, InternshipRegistry.class);
		if (!internshipRegistryRepository.existsById(internshipRegistry.getId()))
			throw new EntityNotFoundException("Internship Registry not found!");
			log.warn("Internship registry with ID {} not found", internshipRegistry.getId());
			
		internshipRegistry.setCreateDate(internshipRegistryRepository.getById(internshipRegistry.getId()).getCreateDate());
		internshipRegistry.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		try {
			internshipRegistry = internshipRegistryRepository.save(internshipRegistry);
            log.info("Internship registry updated: {}", internshipRegistry);
        } catch (Exception e) {
            log.error("Error occurred while updating internship registry: {}", e.getMessage());
            throw e;
        }
		return modelMapper.map(internshipRegistry, InternshipRegistryDto.class);
	}

	public InternshipRegistryDto addInternshipRegistry(InternshipRegistryDto internshipRegistryDto) {
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

		return modelMapper.map(internshipRegistry, InternshipRegistryDto.class);
	}
}
