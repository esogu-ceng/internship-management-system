package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Student;

@Slf4j
@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;

	public Student getStudent(long id) {
		try {
			Student student = studentRepository.findById(id).orElse(null);
			if (student == null) {
				log.warn("Girdiğiniz id'ye ait öğrenci bulunmamaktadır.");
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			log.info("{} id'ye sahip öğrenci {} numaralı ,{} {}. ", student.getId(), student.getStudentNo(), student.getName(), student.getSurname());
			return student;
		} catch (EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public Page<Student> getAllStudents(Pageable pageable) {
		if (studentRepository.findAll() == null) {
			log.warn("Öğrenci listesi boş.");
			return null;
		}
		log.info("Öğrenci listesi başarıyla getirildi.");
		return studentRepository.findAll(pageable);
	}

	public Student addStudent(Student student) {
		log.info("Öğrenci başarılı bir şekilde eklendi.");
		return studentRepository.save(student);
	}

	public Student updateStudent(Student student) {
		if (!studentRepository.existsById(student.getId())) {
			log.warn("{} id'ye sahip öğrenci bulunmamaktadır.", student.getId());
			throw new EntityNotFoundException("Student not found!");
		}
		log.info("Öğrencinin bilgileri güncellendi.");
		student.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		return studentRepository.save(student);
	}

	@Transactional
	public boolean deleteStudent(long id) {
		if (!studentRepository.existsById(id)) {
			log.warn("Girdiğiniz id'ye sahip öğrenci bulunmadığı için silme işlemi gerçekleştirilemedi.");
			return false;
		}
		studentRepository.deleteById(id);
		log.info("Girdiğiniz id'ye sahip öğrenci silindi.");
		return true;
	}

	// TODO
	public Student getStudentByUserId() {
		return null;
	}

}
