package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.requests.FacultyRequestDto;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Service
public class FacultyService {
	@Autowired
	private FacultyRepository facultyRepository;
	private ModelMapper modelMapper;

	public Page<FacultyDto> getFaculties(Pageable pageable) {
		try {
			modelMapper = new ModelMapper();
			log.info("Getting faculties with pageable: {}", pageable);
			Page<Faculty> faculties = facultyRepository.findAll(pageable);
			Page<FacultyDto> facultyDtos = faculties.map(faculty -> modelMapper.map(faculty, FacultyDto.class));
			return facultyDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting faculties: {}", e.getMessage());
			throw e;
		}
	}

	public FacultyDto addFaculty(FacultyDto facultyDto) {
		try {
			modelMapper = new ModelMapper();
			Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
			LocalDateTime dateTime = LocalDateTime.now();
			faculty.setCreateDate(dateTime);
			faculty.setUpdateDate(dateTime);
			facultyRepository.save(faculty);
			log.info("Faculty saved: {}", faculty);
			return modelMapper.map(faculty, FacultyDto.class);
		} catch (Exception e) {
			log.error("Failed to add faculty. Error message: {}", e.getMessage());
			throw e;
		}
	}

	public FacultyDto updateFaculty(FacultyRequestDto facultyRequestDto) {
		modelMapper = new ModelMapper();
		if (facultyRequestDto.getId() == null) {
			log.warn("Faculty Id cannot be null");
			throw new IllegalArgumentException("Faculty Id cannot be null");
		}
		Faculty faculty = facultyRepository.findById(facultyRequestDto.getId())
				.orElseThrow(() -> new EntityNotFoundException("Faculty not found!"));

		faculty = modelMapper.map(facultyRequestDto, Faculty.class);
		faculty.setCreateDate(facultyRepository.getById(faculty.getId()).getCreateDate());
		faculty.setUpdateDate(LocalDateTime.now());
		Faculty updatedFaculty;
		try {
			updatedFaculty = facultyRepository.save(faculty);
			log.info("Faculty updated: {}", updatedFaculty);
		} catch (Exception e) {
			log.error("Error occurred while updating faculty : {}", e.getMessage());
			throw e;
		}
		return modelMapper.map(updatedFaculty, FacultyDto.class);
	}

	public boolean deleteFaculty(Long id) {
		try {
			if (!facultyRepository.existsById(id)) {
				log.warn("Faculty with ID {} not found", id);
				return false;
			}
			facultyRepository.deleteById(id);
			log.info("Faculty deleted with id: {}", id);
			return true;
		} catch (Exception e) {
			log.error("Failed to delete faculty with ID {}. Error message: {}", id, e.getMessage());
			return false;
		}
	}

	public FacultyDto getFacultyById(Long id) {
		Faculty faculty = facultyRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Faculty not found with id: " + id));
		log.info("Faculty found with id: {}", id);
		return convertToDto(faculty);
	}

	private FacultyDto convertToDto(Faculty faculty) {
		modelMapper = new ModelMapper();
		log.info("Converting faculty to FacultyDtoId: {}, FacultyDtoName: {}", faculty.getId(),faculty.getName());

		return modelMapper.map(faculty, FacultyDto.class);
	}

}
