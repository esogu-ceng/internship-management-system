package tr.edu.ogu.ceng.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.InternshipRegistriesRepository;
import tr.edu.ogu.ceng.model.InternshipRegistries;

@Service
public class InternshipRegistriesService {
	
	@Autowired
	private InternshipRegistriesRepository internshipRegistriesRepository;
	
	public InternshipRegistries updateInternshipRegistries(InternshipRegistries internshipRegistries) {
		if (!internshipRegistriesRepository.existsById(internshipRegistries.getId())) throw new EntityNotFoundException("Internship Registry not found!");

		return internshipRegistriesRepository.save(internshipRegistries);
	}
	
}
