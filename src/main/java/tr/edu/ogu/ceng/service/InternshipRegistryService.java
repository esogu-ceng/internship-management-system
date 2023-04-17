package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.model.InternshipRegistry;

@Service
@AllArgsConstructor
public class InternshipRegistryService {

	@Autowired
	private InternshipRegistryRepository internshipRegistryRepository;

	public boolean deleteInternshipRegistry(long id) {
		if (!internshipRegistryRepository.existsById(id)) {
			return false;
		}
		internshipRegistryRepository.deleteById(id);
		return true;
	}

	public InternshipRegistry updateInternshipRegistry(InternshipRegistry internshipRegistry) {
		if (!internshipRegistryRepository.existsById(internshipRegistry.getId()))
			throw new EntityNotFoundException("Internship Registry not found!");

		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internshipRegistry.setUpdateDate(localDateTime);

		return internshipRegistryRepository.save(internshipRegistry);
	}

	public InternshipRegistry addInternshipRegistry(InternshipRegistry internshipRegistry) {
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internshipRegistry.setCreateDate(localDateTime);

		internshipRegistry = internshipRegistryRepository.save(internshipRegistry);

		return internshipRegistry;
	}
}
