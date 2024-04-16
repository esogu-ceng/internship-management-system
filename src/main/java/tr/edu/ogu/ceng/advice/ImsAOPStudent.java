package tr.edu.ogu.ceng.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.service.Exception.ServiceException;

@Aspect
@Component
public class ImsAOPStudent {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageResource messageResource;

	@Before("execution(* tr.edu.ogu.ceng.service.StudentService.addStudent(..)) && args(studentDto)")
	public void beforeAddStudent(StudentDto studentDto) {
		boolean existingStudentTcNo = studentRepository.existsByTckn(studentDto.getTckn());
		boolean existingStudentStudenNo = studentRepository.existsByStudentNo(studentDto.getStudentNo());
		if (existingStudentTcNo && existingStudentStudenNo) {
			throw new ServiceException(messageResource.getMessage("student.already.registered"));
		} else if (existingStudentTcNo) {
			throw new ServiceException(messageResource.getMessage("tckn.already.registered"));
		} else if (existingStudentStudenNo) {
			throw new ServiceException(messageResource.getMessage("student.no.already.registered"));
		}
	}

	@Before("execution(* tr.edu.ogu.ceng.service.StudentService.registerAsStudent(..)) && args(request)")
	public void beforeRegisterAsStudent(StudentDto request) {
		validateStudentNo(request.getStudentNo());
		validateTckno(request.getTckn());
		checkIfStudentAlreadyRegistered(request.getStudentNo(), request.getTckn(), request.getEmail());
		checkIfPasswordsMatchingValidation(request);

	}

	private void checkIfStudentAlreadyRegistered(String studentNo, String tckno, String email) {
		boolean existingStudentTcNo = studentRepository.existsByTckn(tckno);
		boolean existingStudentStudenNo = studentRepository.existsByStudentNo(studentNo);
		boolean existingEmail = userRepository.existsByEmail(email);

		if (existingStudentTcNo && existingStudentStudenNo) {
			throw new ServiceException(messageResource.getMessage("student.already.registered"));
		} else if (existingStudentTcNo) {
			throw new ServiceException(messageResource.getMessage("tckn.already.registered"));
		} else if (existingStudentStudenNo) {
			throw new ServiceException(messageResource.getMessage("student.no.already.registered"));
		} else if (existingEmail) {
			throw new ServiceException(messageResource.getMessage("email.already.registered"));
		}

	}

	private void validateTckno(String tckno) {
		String regex = "^[0-9]{11}$";
		if (!tckno.matches(regex)) {
			throw new ServiceException(messageResource.getMessage("tckn.invalid"));
		}
	}

	private void validateStudentNo(String studentNo) {
		String regex = "^[0-9]+$";
		if (!studentNo.matches(regex)) {
			throw new ServiceException(messageResource.getMessage("student.no.invalid"));
		}
	}

	private void checkIfPasswordsMatchingValidation(StudentDto request) {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new ServiceException(messageResource.getMessage("password.mismatch"));
	}

}
