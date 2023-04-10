package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
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
	public Internship addInternship(Internship internship) {
		return internshipRepository.save(internship);
	}
	public Internship updateInternship(Internship internship) {
		if (!internshipRepository.existsById(internship.getId())) throw new EntityNotFoundException("Internship not found!");
		
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internship.setUpdateDate(localDateTime);
		
		return internshipRepository.save(internship);
	}
	
	public Optional<Internship> getInternship(Long id) {
		if (!internshipRepository.existsById(id)) throw new EntityNotFoundException("Internship not found!");
		
		return internshipRepository.findById(id);
	}
	
	public boolean deleteInternship(Long id) {
		if (!internshipRepository.existsById(id)) return false;
		internshipRepository.deleteById(id);
		return true;
	}

	
}
