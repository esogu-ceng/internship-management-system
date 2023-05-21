package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.dto.responses.InternshipRegistryResponseDto;
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
    private UserRepository userRepository;
    private ModelMapper modelMapper;
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
    public FacultySupervisorResponseDto addFacultySupervisor(FacultySupervisorRequestDto facultySupervisorRequestDto) {
        modelMapper = new ModelMapper();
        LocalDateTime now = LocalDateTime.now();

        // We need to save user before student.
        User user = modelMapper.map(facultySupervisorRequestDto.getUser(), User.class);
        user.setCreateDate(now);
        user.setUpdateDate(now);
        user.setUserType(UserType.FACULTYSUPERVISOR);

        FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisorRequestDto, FacultySupervisor.class);
        facultySupervisor.setUser(userRepository.save(user));// FIXME instead, do we need to call to Service method?
        // But the Service method needs to get DTO. Or are there
        // any other approaches to persist the User?
        facultySupervisor.setCreateDate(now);
        facultySupervisor.setUpdateDate(now);

        FacultySupervisor savedfacultySupervisor = facultySupervisorRepository.save(facultySupervisor);
        log.info("The student was successfully added: {}", savedfacultySupervisor);

        return modelMapper.map(savedfacultySupervisor, FacultySupervisorResponseDto.class);
    }

    public FacultySupervisorResponseDto updateFacultySupervisor(FacultySupervisorRequestDto facultySupervisorRequestDto) {

        FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisorRequestDto, FacultySupervisor.class);
        if (!facultySupervisorRepository.existsById(facultySupervisor.getId()))
            throw new EntityNotFoundException("Faculty supervisor not found!");

        try {
			LocalDateTime now = LocalDateTime.now();
			facultySupervisor.setUpdateDate(now);
			facultySupervisor.setCreateDate(facultySupervisorRepository.getById(facultySupervisor.getId()).getCreateDate());
			FacultySupervisor updatedFacultySupervisor = facultySupervisorRepository.save(facultySupervisor);

			FacultySupervisorResponseDto responseDto = modelMapper.map(updatedFacultySupervisor, FacultySupervisorResponseDto.class);
			responseDto.setFacultyId(facultySupervisorRequestDto.getFaculty().getId());
			responseDto.setUserId(facultySupervisorRequestDto.getUser().getId());
			log.info("Faculty supervisor updated: {}", updatedFacultySupervisor);
			return responseDto;
		} catch (Exception e) {
			log.error("Error occurred while updating faculty supervisor: {}", e.getMessage());
			throw e;
		}
    }
    
    public FacultySupervisorResponseDto getFacultySupervisor(Long id) {
        if (!facultySupervisorRepository.existsById(id)) {
            String message = messageResource.getMessage("not.found");
            throw new EntityNotFoundException(message);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(facultySupervisorRepository.getById(id), FacultySupervisorResponseDto.class);
		} catch (Exception e) {
            log.error("Error occurred while getting faculty supervisor: {}", e.getMessage());
            throw e;
        }
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
    
    public Page<FacultySupervisorResponseDto> listAllFacultySupervisors(Pageable pageable) {
        try {
            Page<FacultySupervisor> facultySupervisors = facultySupervisorRepository.findAll(pageable);
            if (facultySupervisors.isEmpty()) {
                log.warn("The faculty supervisor list is empty.");
            }
            Page<FacultySupervisorResponseDto> facultySupervisorDtos = facultySupervisors.map(
                    facultySupervisor -> modelMapper.map(facultySupervisor, FacultySupervisorResponseDto.class));
            return facultySupervisorDtos;
        } catch (Exception e) {
            log.error("An error occurred while getting faculty supervisors: {}", e.getMessage());
            throw e;
        }
    }

}