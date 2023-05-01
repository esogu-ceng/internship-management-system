package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.enums.UserTypeEnum;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
public class FacultySupervisorService {

	private FacultySupervisorRepository facultySupervisorRepository;
	private UserRepository userRepository;
	private UserTypeRepository userTypeRepository;
	private ModelMapper modelMapper;

	/**
	 * Adds a new Faculty Supervisor and related User definition
	 * 
	 * @param facultySupervisorDto
	 * @return
	 */
	// IMPORTANT: without @Transaction, the user entity may be saved but
	// FacultySupervisor may not be saved because of some different constraints
	@Transactional
	public FacultySupervisorDto addFacultySupervisor(FacultySupervisorDto facultySupervisorDto) {
		FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisorDto, FacultySupervisor.class);

		// need to add a new user to the DB
		User user = facultySupervisor.getUser();
		LocalDateTime now = LocalDateTime.now(); // LocalDateTime is a better choice than TimeStamp which has deprecated
													// functionality
		user.setCreateDate(now);
		user.setUpdateDate(now);
		user.setUserType(userTypeRepository.findByType(UserTypeEnum.FACULTYSUPERVISOR.name()));

		// give persisted entity to the facultySupervisor Object
		facultySupervisor.setUser(userRepository.save(user)); // FIXME instead, do we need to call to Service method?
																// But the Service method needs to get DTO. Or are there
																// any other approaches to persist the User?
		facultySupervisor.setCreateDate(now);
		facultySupervisor.setUpdateDate(now);

		FacultySupervisor savedFacultySupervisor = facultySupervisorRepository.save(facultySupervisor);
		log.info("Faculty supervisor saved: {}", savedFacultySupervisor);
		return modelMapper.map(savedFacultySupervisor, FacultySupervisorDto.class);
	}

	public FacultySupervisorDto updateFacultySupervisor(FacultySupervisorDto facultySupervisordto) {

		FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisordto, FacultySupervisor.class);
		if (!facultySupervisorRepository.existsById(facultySupervisor.getId()))
			throw new EntityNotFoundException("Faculty supervisor not found!");
		LocalDateTime now = LocalDateTime.now();
		facultySupervisor.setUpdateDate(now);
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
		try {
			facultySupervisorRepository.deleteById(id);
			log.info("Faculty supervisor deleted with id: {}", id);
			return true;
		} catch (DataIntegrityViolationException e) {
			log.warn("Cannot delete faculty supervisor with ID {} due to integrity violation", id);
			return false;
		} catch (EmptyResultDataAccessException e) {
			log.warn("Faculty supervisor with ID {} not found", id);
			return false;
		}
	}

}