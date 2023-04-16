package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.model.Internship;

@Service
public class InternshipService {
	@Autowired
	private InternshipRepository internshipRepository;

	public Internship addInternship(Internship internship) {
		return internshipRepository.save(internship);
	}

	public InternshipDto updateInternship(InternshipDto internshipDto) {
		if (!internshipRepository.existsById(internshipDto.getId()))
			throw new EntityNotFoundException("Internship not found!");

		ModelMapper modelMapper = new ModelMapper();
		Internship internship = modelMapper.map(internshipDto, Internship.class);

		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internship.setUpdateDate(localDateTime);

		Internship updatedInternship = internshipRepository.save(internship);
		return modelMapper.map(updatedInternship, InternshipDto.class);
	}

	public Optional<Internship> getInternship(Long id) {
		if (!internshipRepository.existsById(id))
			throw new EntityNotFoundException("Internship not found!");

		return internshipRepository.findById(id);
	}

	public boolean deleteInternship(Long id) {
		if (!internshipRepository.existsById(id))
			return false;
		internshipRepository.deleteById(id);
		return true;
	}

	public Internship approveInternship(Long id) {

		if (!internshipRepository.existsById(id))
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException("Internship not found!");

		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		Internship internship = internshipRepository.findById(id).orElse(null);
		internship.setStatus("Approved");
		internship.setUpdateDate(localDateTime);

		return internshipRepository.save(internship);

	}

}
