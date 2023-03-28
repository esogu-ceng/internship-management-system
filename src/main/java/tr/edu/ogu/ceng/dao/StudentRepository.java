package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}