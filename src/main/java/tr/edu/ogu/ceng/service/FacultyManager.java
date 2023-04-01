package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dto.requests.CreateFacultyRequest;
import tr.edu.ogu.ceng.dto.responses.CreateFacultyResponse;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.utilities.mappers.ModelMapperService;

@Service
@AllArgsConstructor
public class FacultyManager implements FacultyService {

	private ModelMapperService modelMapperService;
	private FacultyRepository facultyRepository;
	

	@Override
	public CreateFacultyResponse add(CreateFacultyRequest createFacultyRequest) {
		
    Faculty faculty = this.modelMapperService.forRequest().map(createFacultyRequest, Faculty.class);
    Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
    faculty.setCreateDate(localDateTime);
    faculty.setUpdateDate(localDateTime);
    this.facultyRepository.save(faculty);
    CreateFacultyResponse createFacultyResponse= this.modelMapperService.forResponse().map(faculty, CreateFacultyResponse.class);
    return createFacultyResponse;
	}

}
