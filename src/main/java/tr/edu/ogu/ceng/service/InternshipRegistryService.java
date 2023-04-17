package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.dto.InternshipRegistryDto;
import tr.edu.ogu.ceng.model.InternshipRegistry;

@Service
@AllArgsConstructor
public class InternshipRegistryService {

	@Autowired
	private InternshipRegistryRepository internshipRegistryRepository;
	private ModelMapper modelMapper;

	public boolean deleteInternshipRegistry(long id) {
		if (!internshipRegistryRepository.existsById(id)) {
			return false;
		}
		internshipRegistryRepository.deleteById(id);
		return true;
	}

	public InternshipRegistryDto updateInternshipRegistry(InternshipRegistryDto internshipRegistryDto) {
		InternshipRegistry internshipRegistry = modelMapper.map(internshipRegistryDto, InternshipRegistry.class);
		if (!internshipRegistryRepository.existsById(internshipRegistry.getId()))
			throw new EntityNotFoundException("Internship Registry not found!");

		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internshipRegistry.setUpdateDate(localDateTime);

		return modelMapper.map(internshipRegistryRepository.save(internshipRegistry), InternshipRegistryDto.class);
	}

	public InternshipRegistryDto addInternshipRegistry(InternshipRegistryDto internshipRegistryDto) {
		InternshipRegistry internshipRegistry = modelMapper.map(internshipRegistryDto, InternshipRegistry.class);
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		internshipRegistry.setCreateDate(localDateTime);

		internshipRegistry = internshipRegistryRepository.save(internshipRegistry);

		return modelMapper.map(internshipRegistryRepository.save(internshipRegistry), InternshipRegistryDto.class);
	}
}
