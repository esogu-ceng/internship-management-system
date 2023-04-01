package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.Internship;

public interface InternshipRepository extends JpaRepository <Internship, Long>{
	
}
