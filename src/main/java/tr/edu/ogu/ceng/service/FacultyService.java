package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.User;

import java.time.LocalDateTime;
@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public Faculty saveFaculty(Faculty faculty){
        LocalDateTime localDateTime = LocalDateTime.now();
        faculty.setCreateDate(localDateTime);
        faculty.setUpdateDate(localDateTime);
        return facultyRepository.save(faculty);
    }
}
