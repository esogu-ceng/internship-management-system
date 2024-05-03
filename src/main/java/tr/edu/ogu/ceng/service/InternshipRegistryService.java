
package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.dto.requests.InternshipRegistryRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipRegistryResponseDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.InternshipRegistry;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class InternshipRegistryService {

	private InternshipRegistryRepository internshipRegistryRepository;
	private MessageResource messageResource;

	public boolean deleteInternshipRegistry(long id) {
		if (!internshipRegistryRepository.existsById(id)) {
			log.warn("Internship registry with ID {} not found", id);
      return false;
		} else {
			internshipRegistryRepository.deleteById(id);
			log.info("Internship registry deleted with ID: {}", id);
		}
		return true;
	}

	public InternshipRegistryResponseDto updateInternshipRegistry(InternshipRegistryRequestDto requestDto) {
		ModelMapper modelMapper = new ModelMapper();
		InternshipRegistry internshipRegistry = modelMapper.map(requestDto, InternshipRegistry.class);
		if (!internshipRegistryRepository.existsById(internshipRegistry.getId())) {
			log.warn("Internship registry with ID {} not found", internshipRegistry.getId());
			throw new EntityNotFoundException(messageResource.getMessage("internshipRegistryNotFound"));
		}

		try {
			LocalDateTime dateTime = LocalDateTime.now();
			internshipRegistry
					.setCreateDate(internshipRegistryRepository.getReferenceById(internshipRegistry.getId()).getCreateDate());
			internshipRegistry.setUpdateDate(dateTime);
			InternshipRegistry updatedInternshipRegistry = internshipRegistryRepository.save(internshipRegistry);

			log.info("Internship registry updated id: {}, name: {}", updatedInternshipRegistry.getId(), updatedInternshipRegistry.getName());
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

			log.info("Internship registry added id: {}, name: {}", addedInternshipRegistry.getId(), addedInternshipRegistry.getName());
			return modelMapper.map(addedInternshipRegistry, InternshipRegistryResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while saving internship registry: {}", e.getMessage());
			throw e;
		}
	}

	public Page<InternshipRegistryResponseDto> getAllInternshipRegistiries(Long internshipId, Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();

			Page<InternshipRegistry> internshipRegistries = internshipRegistryRepository
					.findAllByInternshipId(internshipId, pageable);
			if (internshipRegistries.isEmpty()) {
				log.warn("The internship registry list is empty.");
			}
			Page<InternshipRegistryResponseDto> internshipRegistryDtos = internshipRegistries.map(
					internshipRegistry -> modelMapper.map(internshipRegistry, InternshipRegistryResponseDto.class));
			log.info("Getting all internship registries");
			return internshipRegistryDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internship registries: {}", e.getMessage());
			throw e;
		}
	}

	public InternshipRegistryResponseDto getInternshipRegistry(Long id) {
		if (!internshipRegistryRepository.existsById(id)) {
			log.warn("Internship registry with ID {} not found", id);
			throw new EntityNotFoundException(messageResource.getMessage("internshipRegistryNotFound"));
		}
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting internship registry by id: {}", id);
			return modelMapper.map(internshipRegistryRepository.getReferenceById(id), InternshipRegistryResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while getting internship registry: {}", e.getMessage());
			throw e;
		}
	}

}
