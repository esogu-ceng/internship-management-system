package tr.edu.ogu.ceng.service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.model.Internship;

@Service
public class InternshipService {

	@Autowired
	private InternshipRepository internshipRepository;
	
	public Internship updateInternship(Internship internship) {
		if (!internshipRepository.existsById(internship.getId())) throw new EntityNotFoundException("Internship not found!");
		
		internship.setId(internship.getId());
		
		return internshipRepository.save(internship);
	}
	
	public Optional<Internship> getInternship(Long id) {
		if (!internshipRepository.existsById(id)) throw new EntityNotFoundException("Internship not found!");
		
		return internshipRepository.findById(id);
	}
	
}
