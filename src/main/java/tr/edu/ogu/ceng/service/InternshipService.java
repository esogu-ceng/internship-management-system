package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.model.Internship;

@Slf4j
@Service
public class InternshipService {
	@Autowired
	private InternshipRepository internshipRepository;
	public Internship addInternship(Internship internship) {
		return internshipRepository.save(internship);
	}
	public Internship updateInternship(Internship internship) {
		if (!internshipRepository.existsById(internship.getId())) {
			log.error("Internship not found");
			throw new EntityNotFoundException("Internship not found!");
		}
		
		try {
			
			Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
			internship.setUpdateDate(localDateTime);
			var updatedInternship = internshipRepository.save(internship);
			log.info("Internship date time updated successfully.");
			return updatedInternship;
			
		} catch (Exception e) {
			log.error("Failed to update internship date time. Error message: {}", e.getMessage());
			throw e;
		}	
		
	}
	
	public Optional<Internship> getInternship(Long id) {
		if (!internshipRepository.existsById(id)) {
			log.error("Internship not found with id {}", id);
			throw new EntityNotFoundException("Internship not found!");
		}
			
		
		return internshipRepository.findById(id);
	}
	
	public boolean deleteInternship(Long id) {
		if (!internshipRepository.existsById(id)) {
			log.error("Internship not found with id {}", id);
			return false;
		}
			
		internshipRepository.deleteById(id);
		log.info("Internship deleted successfully.");
		return true;
	}

	
}
