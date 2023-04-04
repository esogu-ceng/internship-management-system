package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.ceng.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
