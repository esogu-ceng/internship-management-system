package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dto.requests.UpdateStudentRequest;
import tr.edu.ogu.ceng.dto.responses.UpdateStudentResponse;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.utilities.mappers.*;

@Service
@AllArgsConstructor
public class StudentManager implements StudentService{

	private ModelMapperService modelMapperService;
	private StudentRepository studentRepository;

	@Override
	public UpdateStudentResponse update(long id, UpdateStudentRequest updateStudentRequest) {
		Student student = this.modelMapperService.forRequest().map(updateStudentRequest, Student.class);
		student.setId(id);
		student.setUpdate_date(new Timestamp(System.currentTimeMillis()));
		this.studentRepository.save(student); 
		UpdateStudentResponse updateStudentResponse=this.modelMapperService.forResponse().map(student, UpdateStudentResponse.class);
		return updateStudentResponse;
	}

}
