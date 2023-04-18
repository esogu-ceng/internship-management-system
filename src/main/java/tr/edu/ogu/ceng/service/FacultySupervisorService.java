package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
public class FacultySupervisorService {

	@Autowired
	private FacultySupervisorRepository facultySupervisorRepository;
	private ModelMapper modelMapper;

	public FacultySupervisorDto saveFacultySupervisor(FacultySupervisorDto facultySupervisordto) {
		FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisordto, FacultySupervisor.class);
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		facultySupervisor.setCreateDate(localDateTime);
		facultySupervisor.setUpdateDate(localDateTime);
		FacultySupervisor savedFacultySupervisor;
		try {
			savedFacultySupervisor = facultySupervisorRepository.save(facultySupervisor);
			log.info("Faculty supervisor saved: {}", savedFacultySupervisor);
		} catch (Exception e) {
			log.error("Error occurred while saving faculty supervisor: {}", e.getMessage());
			throw e;
		}
		return modelMapper.map(savedFacultySupervisor, FacultySupervisorDto.class);
	}

	public FacultySupervisor updateFacultySupervisor(FacultySupervisor facultySupervisor) {
		if (!facultySupervisorRepository.existsById(facultySupervisor.getId()))
			throw new EntityNotFoundException("Faculty supervisor not found!");
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		facultySupervisor.setUpdateDate(localDateTime);
		FacultySupervisor updatedFacultySupervisor;
		try {
			updatedFacultySupervisor = facultySupervisorRepository.save(facultySupervisor);
			log.info("Faculty supervisor updated: {}", updatedFacultySupervisor);
		} catch (Exception e) {
			log.error("Error occurred while updating faculty supervisor: {}", e.getMessage());
			throw e;
		}
		return updatedFacultySupervisor;
	}

	public FacultySupervisorDto updateFacultySupervisor(FacultySupervisorDto facultySupervisordto) {

		FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisordto, FacultySupervisor.class);
		if (!facultySupervisorRepository.existsById(facultySupervisor.getId()))
			throw new EntityNotFoundException("Faculty supervisor not found!");
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		facultySupervisor.setUpdateDate(localDateTime);
		FacultySupervisor updatedFacultySupervisor;
		try {
			updatedFacultySupervisor = facultySupervisorRepository.save(facultySupervisor);
			log.info("Faculty supervisor updated: {}", updatedFacultySupervisor);
		} catch (Exception e) {
			log.error("Error occurred while updating faculty supervisor: {}", e.getMessage());
			throw e;
		}
		return modelMapper.map(updatedFacultySupervisor, FacultySupervisorDto.class);
	}

	public FacultySupervisorDto getFacultySupervisor(Long id) {
		if (!facultySupervisorRepository.existsById(id))
			throw new EntityNotFoundException("Faculty supervisor not found!");
		Optional<FacultySupervisor> facultySupervisorOptional = facultySupervisorRepository.findById(id);

		facultySupervisorOptional.ifPresent(facultySupervisor -> log.info("Faculty supervisor retrieved: {}",
				modelMapper.map(facultySupervisorOptional.get(), FacultySupervisorDto.class)));
		return modelMapper.map(facultySupervisorOptional.get(), FacultySupervisorDto.class);
	}

	public boolean deleteFacultySupervisor(long id) {
		if (!facultySupervisorRepository.existsById(id))
			throw new EntityNotFoundException("Faculty Supervisor Not Found!");

		facultySupervisorRepository.deleteById(id);
		log.info("Faculty supervisor deleted with id: {}", id);
		return true;
	}

}
