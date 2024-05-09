package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import tr.edu.ogu.ceng.dto.requests.UserTypeAndCountDto;
import tr.edu.ogu.ceng.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	boolean existsByEmail(String email);

	@Query("SELECT u.userType, COUNT(u) FROM User u GROUP BY u.userType")
	List<Object[]> countByUserType();
}
