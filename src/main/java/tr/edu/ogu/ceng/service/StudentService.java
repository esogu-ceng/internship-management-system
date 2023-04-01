package tr.edu.ogu.ceng.service;

import tr.edu.ogu.ceng.dto.requests.*;
import tr.edu.ogu.ceng.dto.responses.UpdateStudentResponse;

public interface StudentService {

    UpdateStudentResponse update(long id , UpdateStudentRequest updateStudentRequest);
}
