package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.model.Faculty;

@Service
public class FacultyService {
	@Autowired
	private FacultyRepository facultyRepository;

	public List<Faculty> getFaculties() {
		return facultyRepository.findAll();
	}

	public Faculty addFaculty(Faculty faculty) {
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		faculty.setCreateDate(localDateTime);
		faculty.setUpdateDate(localDateTime);
		return facultyRepository.save(faculty);
	}

	public Faculty updateFaculty(Faculty faculty) {
		Faculty dbFaculty = facultyRepository.findById(faculty.getId()).orElse(null);
		if (dbFaculty == null)
			return null;
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		dbFaculty.setUpdateDate(localDateTime);
		return facultyRepository.save(dbFaculty);
	}

	public boolean deleteFaculty(Long id) {
		if (!facultyRepository.existsById(id))
			return false;
		facultyRepository.deleteById(id);
		return true;
	}
}
