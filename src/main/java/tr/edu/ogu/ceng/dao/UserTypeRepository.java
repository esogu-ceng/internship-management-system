package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
	UserType findByType(String type);

}
