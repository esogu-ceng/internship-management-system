package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.ceng.model.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long> {
	Setting findByKey(String key);
}