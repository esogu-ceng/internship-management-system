package tr.edu.ogu.ceng.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanySupervisorRepository;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.model.CompanySupervisor;
@Slf4j
@AllArgsConstructor
@Service
public class CompanySupervisorService {

	private final CompanySupervisorRepository repository;
    private final ModelMapper mapper;
    
    public List<CompanySupervisorDto> getAll() {

        List<CompanySupervisor> companySupervisors = repository.findAll();
        List<CompanySupervisorDto> response = companySupervisors
                .stream()
                .map(companySupervisor -> mapper.map(companySupervisor, CompanySupervisorDto.class))
                .toList();

        return response;
    }

    public CompanySupervisorDto getById(int id) {
    	CompanySupervisor companySupervisor = repository.findById(id).orElseThrow();
    	CompanySupervisorDto response = mapper.map(companySupervisor,CompanySupervisorDto.class);
        return response;
    }

    public CompanySupervisorDto add(CompanySupervisorDto request) {
    	CompanySupervisor companySupervisor = mapper.map(request,CompanySupervisor.class);
    	companySupervisor.setId((long) 0);
    	CompanySupervisor createdCompanySupervisor = repository.save(companySupervisor);

    	CompanySupervisorDto response = mapper.map(createdCompanySupervisor,CompanySupervisorDto.class);
        return response;
    }

    public CompanySupervisorDto update(CompanySupervisorDto request) {
    	CompanySupervisor companySupervisor = mapper.map(request,CompanySupervisor.class);
    	companySupervisor.setId(request.getId());
        repository.save(companySupervisor);

        CompanySupervisorDto response = mapper.map(companySupervisor,CompanySupervisorDto.class);
        return response;
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
