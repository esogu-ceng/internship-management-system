package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanySupervisorRepository;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.dto.requests.CompanySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.CompanySupervisorResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.UserAlreadyExistsException;

@Slf4j
@AllArgsConstructor
@Service
public class CompanySupervisorService {

	private final CompanySupervisorRepository repository;
	private final ModelMapper mapper;
	private final UserService userService;

	public Page<CompanySupervisorResponseDto> getAll(Pageable pageable) {

		Page<CompanySupervisor> companySupervisors = repository.findAll(pageable);
		Page<CompanySupervisorResponseDto> response = companySupervisors
				.map(companySupervisor -> mapper.map(companySupervisor, CompanySupervisorResponseDto.class));

		return response;
	}

	public CompanySupervisorResponseDto getById(Long id) {
		CompanySupervisor companySupervisor = repository.findById(id).orElseThrow();
		CompanySupervisorResponseDto response = mapper.map(companySupervisor, CompanySupervisorResponseDto.class);
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

	public CompanySupervisorResponseDto addCompany(CompanySupervisorRequestDto request) {

		LocalDateTime now = LocalDateTime.now();

		User user = mapper.map(request.getUser(), User.class);
		user.setCreateDate(now);
		user.setUpdateDate(now);
		user.setUserType(UserType.COMPANYSUPERVISOR);

		checkIfCompanySupervisorExistsByUserId(request.getUser().getId());
		CompanySupervisor companySupervisor = mapper.map(request, CompanySupervisor.class);
		companySupervisor.setUser(userService.saveUser(user));
		companySupervisor.setCreateDate(now);
		companySupervisor.setUpdateDate(now);
		CompanySupervisor createdCompanySupervisor = repository.save(companySupervisor);

		return mapper.map(createdCompanySupervisor, CompanySupervisorResponseDto.class);

	}

	public CompanySupervisorDto update(CompanySupervisorDto request) {
		CompanySupervisor companySupervisor = repository.findById(request.getId())
				.orElseThrow(() -> new EntityNotFoundException("Company Supervisor not found!"));
		if (companySupervisor.getUser().getId() != request.getUser().getId()) {
			// checkIfCompanySupervisorExistsByUserId(request.getUser().getId());
		}
		request.setCreateDate(companySupervisor.getCreateDate());
		companySupervisor = mapper.map(request, CompanySupervisor.class);
		companySupervisor.setUpdateDate(LocalDateTime.now());
		CompanySupervisor updatedCompanySupervisor = repository.save(companySupervisor);

		CompanySupervisorDto response = mapper.map(updatedCompanySupervisor, CompanySupervisorDto.class);
		return response;
	}

	public void delete(Long id) {
		repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Company Supervisor not found!"));
		repository.deleteById(id);
	}

	void checkIfCompanySupervisorExistsByUserId(Long userId) {
		if (repository.existsByUserId(userId)) {
			throw new UserAlreadyExistsException();
		}
	}

	public List<CompanySupervisorDto> getCompanySupervisorsByCompanyId(Long companyId) {
		List<CompanySupervisor> companySupervisors = repository.findAllByCompanyId(companyId);

		List<CompanySupervisorDto> companySupervisorDtos = companySupervisors.stream()
				.map(companySupervisor -> mapper.map(companySupervisor, CompanySupervisorDto.class))
				.collect(Collectors.toList());

		return companySupervisorDtos;
	}

	public CompanySupervisorDto getCompanySupervisorByUserId(Long userId) {
		CompanySupervisor companySupervisor = repository.findCompanySupervisorByUserId(userId);
		return mapper.map(companySupervisor, CompanySupervisorDto.class);
	}
}
