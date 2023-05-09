package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanySupervisorRepository;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.UserAlreadyExistsException;

@Slf4j
@AllArgsConstructor
@Service
public class CompanySupervisorService {

	private final CompanySupervisorRepository repository;
	private final ModelMapper mapper;

	public Page<CompanySupervisorDto> getAll(Pageable pageable) {

		Page<CompanySupervisor> companySupervisors = repository.findAll(pageable);
		Page<CompanySupervisorDto> response = companySupervisors
				.map(companySupervisor -> mapper.map(companySupervisor, CompanySupervisorDto.class));

		return response;
	}

	public CompanySupervisorDto getById(Long id) {
		CompanySupervisor companySupervisor = repository.findById(id).orElseThrow();
		CompanySupervisorDto response = mapper.map(companySupervisor, CompanySupervisorDto.class);
		return response;
	}

	public CompanySupervisorDto add(CompanySupervisorDto request) {
		checkIfCompanySupervisorExistsByUserId(request.getUser().getId());
		CompanySupervisor companySupervisor = mapper.map(request, CompanySupervisor.class);
		companySupervisor.setCreateDate(LocalDateTime.now());
		companySupervisor.setUpdateDate(LocalDateTime.now());
		CompanySupervisor createdCompanySupervisor = repository.save(companySupervisor);

		CompanySupervisorDto response = mapper.map(createdCompanySupervisor, CompanySupervisorDto.class);
		return response;
	}

	public CompanySupervisorDto update(CompanySupervisorDto request) {
		CompanySupervisor companySupervisor = repository.findById(request.getId())
				.orElseThrow(() -> new EntityNotFoundException("Company Supervisor not found!"));
		if (companySupervisor.getUser().getId() != request.getUser().getId()) {
			checkIfCompanySupervisorExistsByUserId(request.getUser().getId());
		}
		request.setCreateDate(companySupervisor.getCreateDate());
		companySupervisor = mapper.map(request, CompanySupervisor.class);
		companySupervisor.setUpdateDate(LocalDateTime.now());
		CompanySupervisor updatedCompanySupervisor = repository.save(companySupervisor);

		CompanySupervisorDto response = mapper.map(updatedCompanySupervisor, CompanySupervisorDto.class);
		return response;
	}

	public void delete(Long id) {
		repository.findById(id)
		.orElseThrow(() -> new EntityNotFoundException("Company Supervisor not found!"));
			repository.deleteById(id);
	}

	void checkIfCompanySupervisorExistsByUserId(Long userId) {
		if (repository.existsByUserId(userId)) {
			throw new UserAlreadyExistsException();
		}
	}
}
