package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
	Settings findByKey(String key);
}
