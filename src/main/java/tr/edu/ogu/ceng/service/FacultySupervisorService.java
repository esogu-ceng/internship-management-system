package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Slf4j
@Service
public class FacultySupervisorService {

	@Autowired
	private FacultySupervisorRepository facultySupervisorRepository;

	public FacultySupervisor saveFacultySupervisor(FacultySupervisor facultySupervisor) {
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		facultySupervisor.setCreateDate(localDateTime);
		facultySupervisor.setUpdateDate(localDateTime);
		log.info("Faculty supervisor with ID {} has been saved.", facultySupervisor.getId());
		return facultySupervisorRepository.save(facultySupervisor);

	}

	public FacultySupervisor updateFacultySupervisor(FacultySupervisor facultySupervisor) {
		if (!facultySupervisorRepository.existsById(facultySupervisor.getId())) {
			log.warn("Faculty supervisor with ID {} was not found, therefore the update cannot be performed." + facultySupervisor.getId());
			throw new EntityNotFoundException("Faculty supervisor not found!");
		}
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		facultySupervisor.setUpdateDate(localDateTime);
		log.info("Faculty supervisor is updated");
		return facultySupervisorRepository.save(facultySupervisor);
	}

	public Optional<FacultySupervisor> getFacultySupervisor(Long id) {
		if (!facultySupervisorRepository.existsById(id)) {
			log.warn("Faculty supervisor with the given ID was not found, cannot retrieve");
			throw new EntityNotFoundException("Faculty supervisor not found!");
		}
		log.info("Faculty supervisor with the given ID succesfully retrieved");
		return facultySupervisorRepository.findById(id);
	}

	public boolean deleteFacultySupervisor(long id) {
		if (!facultySupervisorRepository.existsById(id)) {
			log.warn("Faculty supervisor with given ID  was not found, therefore the delete cannot be performed.");
			throw new EntityNotFoundException("Faculty Supervisor Not Found!");
		}
		log.info("Faculty supervisor with the given ID succesfully deleted");
		facultySupervisorRepository.deleteById(id);
		return true;
	}
}
