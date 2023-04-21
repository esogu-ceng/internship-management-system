package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class FacultyService {
	@Autowired
	private FacultyRepository facultyRepository;

	public Page<Faculty> getFaculties(Pageable pageable) {
		log.info("Getting faculties with pageable: {}", pageable);
		return facultyRepository.findAll(pageable);
	}

	public Faculty addFaculty(Faculty faculty) {
		try {
			Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
			faculty.setCreateDate(localDateTime);
			faculty.setUpdateDate(localDateTime);
			facultyRepository.save(faculty);
			log.info("Faculty saved: {}", faculty);
		} catch (Exception e) {
			log.error("Failed to add faculty. Error message: {}", e.getMessage());
			throw e;
		}
		return faculty;
	}

	public Faculty updateFaculty(Faculty faculty) {
		if (!facultyRepository.existsById(faculty.getId()))
			throw new EntityNotFoundException("Faculty not found!");
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		faculty.setUpdateDate(localDateTime);
		Faculty updatedFaculty;
		try {
			updatedFaculty = facultyRepository.save(faculty);
			log.info("Faculty updated: {}", updatedFaculty);
		} catch (Exception e) {
			log.error("Error occurred while updating faculty : {}", e.getMessage());
			throw e;
		}
		return updatedFaculty;
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
}
