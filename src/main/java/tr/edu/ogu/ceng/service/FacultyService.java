package tr.edu.ogu.ceng.service;

import tr.edu.ogu.ceng.dto.requests.CreateFacultyRequest;
import tr.edu.ogu.ceng.dto.responses.CreateFacultyResponse;

public interface FacultyService {
	
	CreateFacultyResponse add(CreateFacultyRequest createFacultyRequest);
}
