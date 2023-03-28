package tr.edu.ogu.ceng.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.model.Internship;

@Service
public class InternshipService {

	@Autowired
	private InternshipRepository internshipRepository;
	
	public Internship updateInternship(Internship internship,Long id) {
		if (!internshipRepository.existsById(id)) return null;

		internship.setId(id);
		return internshipRepository.save(internship);
	}
	
}
