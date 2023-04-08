package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.model.Faculty;
@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public List<Faculty> getFaculties()
    {
    	return facultyRepository.findAll();
    }
    
    public Faculty addFaculty(Faculty faculty){
        Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
        faculty.setCreateDate(localDateTime);
        faculty.setUpdateDate(localDateTime);
        return facultyRepository.save(faculty);
    }
    
    public boolean deleteFaculty(Long id) {
		if (!facultyRepository.existsById(id)) return false;
		facultyRepository.deleteById(id);
		return true;
	}
    
    public List<Faculty> getAllFaculties(){
        List<Faculty> faculties = new ArrayList<Faculty>();  
        facultyRepository.findAll().forEach(faculty1 -> faculties.add(faculty1));  
        return faculties;
    }
}
