package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.ceng.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
}
