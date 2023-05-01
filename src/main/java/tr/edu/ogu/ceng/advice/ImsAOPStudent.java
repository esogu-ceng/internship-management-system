package tr.edu.ogu.ceng.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.service.Exception.ServiceException;

@Aspect
@Component
public class ImsAOPStudent {
	
	@Autowired
    private StudentRepository studentRepository;

    @Before("execution(* tr.edu.ogu.ceng.service.StudentService.addStudent(..)) && args(studentDto)")
    public void beforeAddStudent(StudentDto studentDto) {
    	boolean existingStudentTcNo = studentRepository.existsByTckn(studentDto.getTckn());
		boolean existingStudentStudenNo = studentRepository.existsByStudentNo(studentDto.getStudentNo());
		if (existingStudentTcNo == true && existingStudentStudenNo == true) {
			throw new ServiceException("Bu TC kimlik numarası ve Öğrenci numarası zaten kayıtlı.");
		} else if (existingStudentTcNo == true) {
			throw new ServiceException("Bu TC kimlik numarası zaten kayıtlı.");
		} else if (existingStudentStudenNo == true) {
			throw new ServiceException("Bu öğrenci numarası zaten kayıtlı.");
		}
    }    
	
}
