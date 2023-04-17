package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tr.edu.ogu.ceng.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long>{
    @Query(value = "SELECT * FROM ims_students u WHERE (:name IS NULL OR u.name = :name) AND (:surname IS NULL OR u.surname = :surname)",
           nativeQuery = true)    
    Page<Student> searchStudent(@Param("name") String name, @Param("surname") String surname, Pageable pageable);
}
