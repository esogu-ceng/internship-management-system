package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.model.Internship;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipService {
	@Autowired
	private InternshipRepository internshipRepository;
	private ModelMapper modelMapper;

	public InternshipDto addInternship(InternshipDto internshipDto) {
		modelMapper = new ModelMapper();
		Internship internship = modelMapper.map(internshipDto, Internship.class);
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internship.setCreateDate(localDateTime);
		internship.setUpdateDate(localDateTime);
		internship = internshipRepository.save(internship);
		
		return modelMapper.map(internship, InternshipDto.class);
	}

	public InternshipDto updateInternship(InternshipDto internshipDto) {
		if (!internshipRepository.existsById(internshipDto.getId()))
			throw new EntityNotFoundException("Internship not found!");

		modelMapper = new ModelMapper();
		Internship internship = modelMapper.map(internshipDto, Internship.class);

		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internship.setCreateDate(internshipRepository.getById(internship.getId()).getCreateDate());
		internship.setUpdateDate(localDateTime);

		internship = internshipRepository.save(internship);
		return modelMapper.map(internship, InternshipDto.class);
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
		internship.setStatus(InternshipStatus.APPROVED);
		internship.setUpdateDate(localDateTime);
		internshipRepository.save(internship);

		log.info("Internship Approved!");
		return internship;

	}

}
