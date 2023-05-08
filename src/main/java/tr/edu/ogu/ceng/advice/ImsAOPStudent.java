package tr.edu.ogu.ceng.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.service.Exception.ServiceException;

@Aspect
@Component
public class ImsAOPStudent {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private UserRepository userRepository;

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

	@Before("execution(* tr.edu.ogu.ceng.service.StudentService.registerAsStudent(..)) && args(request)")
	public void beforeRegisterAsStudent(StudentDto request) {
		validateStudentNo(request.getStudentNo());
		validateTckno(request.getTckn());
		checkIfStudentAlreadyRegistered(request.getStudentNo(), request.getTckn(), request.getEmail(),
				request.getUsername());
		checkIfPasswordsMatchingValidation(request);

	}

	private void checkIfStudentAlreadyRegistered(String studentNo, String tckno, String email, String username) {
		boolean existingStudentTcNo = studentRepository.existsByTckn(tckno);
		boolean existingStudentStudenNo = studentRepository.existsByStudentNo(studentNo);
		boolean existingEmail = userRepository.existsByEmail(email);
		boolean existingUsername = userRepository.existsByUsername(username);

		if (existingStudentTcNo && existingStudentStudenNo) {
			throw new ServiceException("Bu TC kimlik numarası ve Öğrenci numarası zaten kayıtlı.");
		} else if (existingStudentTcNo) {
			throw new ServiceException("Bu TC kimlik numarası zaten kayıtlı.");
		} else if (existingStudentStudenNo) {
			throw new ServiceException("Bu öğrenci numarası zaten kayıtlı.");
		} else if (existingUsername) {
			throw new ServiceException("Bu username zaten kayıtlı");
		} else if (existingEmail) {
			throw new ServiceException("Bu email zaten kayıtlı");
		}

	}

	private void validateTckno(String tckno) {
		String regex = "^[0-9]{11}$";
		if (!tckno.matches(regex)) {
			throw new ServiceException("TCKNO 11 haneli ve sadece sayılardan oluşmalıdır!");
		}
	}

	private void validateStudentNo(String studentNo) {
		String regex = "^[0-9]+$";
		if (!studentNo.matches(regex)) {
			throw new ServiceException("Öğrenci numarası sadece sayılardan oluşmalıdır!");
		}
	}
	
	private void checkIfPasswordsMatchingValidation(StudentDto request) {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new ServiceException("Şifreler eşleşmiyor!");

	}

}
