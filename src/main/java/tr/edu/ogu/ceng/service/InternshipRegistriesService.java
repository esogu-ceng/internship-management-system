package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.InternshipRegistriesRepository;
import tr.edu.ogu.ceng.model.InternshipRegistries;

@Service
public class InternshipRegistriesService {
	
	@Autowired
	private InternshipRegistriesRepository internshipRegistriesRepository;
	

	public InternshipRegistries updateInternshipRegistries(InternshipRegistries internshipRegistries, Long id) {
		if (!internshipRegistriesRepository.existsById(id)) return null;

		internshipRegistries.setId(id);
		return internshipRegistriesRepository.save(internshipRegistries);
	}
	
	public InternshipRegistries addInternshipRegistries(InternshipRegistries internshipRegistries) {
		internshipRegistries = internshipRegistriesRepository.save(internshipRegistries);
	    if (internshipRegistries == null) return null;
	    return internshipRegistries;
	}
	
	
}
