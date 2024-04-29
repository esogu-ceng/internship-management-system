package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
public class FacultySupervisorService {

	private FacultySupervisorRepository facultySupervisorRepository;
	private UserService userService;
	private ModelMapper modelMapper;

	@Autowired
	private MessageResource messageResource;

	/**
	 * Adds a new Faculty Supervisor and related User definition
	 *
	 * @param facultySupervisorRequestDto
	 * @return
	 */
	// IMPORTANT: without @Transaction, the user entity may be saved but
	// FacultySupervisor may not be saved because of some different constraints
	@Transactional
	public FacultySupervisor addFacultySupervisor(FacultySupervisor facultySupervisor) {

		LocalDateTime now = LocalDateTime.now();
		// We need to save user before facultysupervisor.
		User user = facultySupervisor.getUser();
		user.setCreateDate(now);
		user.setUpdateDate(now);
		user.setUserType(UserType.FACULTYSUPERVISOR);

		facultySupervisor.setUser(userService.addUser(user));
		facultySupervisor.setCreateDate(now);
		facultySupervisor.setUpdateDate(now);

		FacultySupervisor savedfacultySupervisor = facultySupervisorRepository.save(facultySupervisor);
		log.info("The faculty supervisor was successfully added: {}", savedfacultySupervisor.getId());

		return savedfacultySupervisor;
	}

	public FacultySupervisorResponseDto updateFacultySupervisor(
			FacultySupervisorRequestDto facultySupervisorRequestDto) {

		FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisorRequestDto, FacultySupervisor.class);
		if (!facultySupervisorRepository.existsById(facultySupervisor.getId()))
			throw new EntityNotFoundException(messageResource.getMessage("facultySupervisorNotFound"));

		try {
			LocalDateTime now = LocalDateTime.now();
			facultySupervisor.setUpdateDate(now);
			User user = userService.GetUserById(facultySupervisor.getUser().getId());
			facultySupervisor.setUser(user);
			facultySupervisor
					.setCreateDate(facultySupervisorRepository.getById(facultySupervisor.getId()).getCreateDate());
			FacultySupervisor updatedFacultySupervisor = facultySupervisorRepository.save(facultySupervisor);

			FacultySupervisorResponseDto responseDto = modelMapper.map(updatedFacultySupervisor,
					FacultySupervisorResponseDto.class);
			responseDto.setFacultyId(facultySupervisorRequestDto.getFaculty().getId());
			responseDto.setUser(responseDto.getUser());

			log.info("Faculty supervisor updated successfully with id: {}", facultySupervisor.getId());
			return responseDto;
		} catch (Exception e) {
			log.error("Error occurred while updating faculty supervisor: {}", e.getMessage());
			throw e;
		}
	}

	public FacultySupervisorResponseDto getFacultySupervisor(Long id) {
		if (!facultySupervisorRepository.existsById(id)) {
			log.warn("Faculty supervisor not found with id: {}", id);
			throw new EntityNotFoundException(messageResource.getMessage("facultySupervisorNotFound"));
		}
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Faculty supervisor found with id: {}", id);
			return modelMapper.map(facultySupervisorRepository.getById(id), FacultySupervisorResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while getting faculty supervisor: {}", e.getMessage());
			throw e;
		}
	}

	public boolean deleteFacultySupervisor(long id) {
		try {
			if(facultySupervisorRepository.existsById(id)) {
				facultySupervisorRepository.deleteById(id);
				log.info("Faculty supervisor deleted with id: {}", id);
			}		
		} catch (DataIntegrityViolationException e) {
			log.warn("Cannot delete faculty supervisor with ID {} due to integrity violation", id);
		} catch (EmptyResultDataAccessException e) {
			log.warn("Faculty supervisor with ID {} not found", id);
		}
		return true;
	}

	public Page<FacultySupervisorResponseDto> getAllFacultySupervisors(Pageable pageable) {
		try {
			Page<FacultySupervisor> facultySupervisors = facultySupervisorRepository.findAll(pageable);
			if (facultySupervisors.isEmpty()) {
				log.warn("The faculty supervisor list is empty.");
			}
			Page<FacultySupervisorResponseDto> facultySupervisorDtos = facultySupervisors
					.map(facultySupervisor -> modelMapper.map(facultySupervisor, FacultySupervisorResponseDto.class));
			log.info("Faculty supervisor list retrieved successfully.");
			return facultySupervisorDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting faculty supervisors: {}", e.getMessage());
			throw e;
		}
	}

	public FacultySupervisorResponseDto getFacultySupervisorByUserId(Long userId) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			FacultySupervisor facultysupervisor = facultySupervisorRepository.findByUserId(userId);
			log.info("Faculty supervisor found with user ID: {}", userId);
			return modelMapper.map(facultysupervisor, FacultySupervisorResponseDto.class);
		} catch (Exception e) {
			log.error("An error occurred while getting facultySupervisor with given user ID", e.getMessage());
			throw e;
		}
	}

}