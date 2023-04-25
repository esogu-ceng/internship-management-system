package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.service.CompanySupervisorService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/company-supervisor")
public class CompanySupervisorController {
	@Autowired
    private final CompanySupervisorService service;
    
    @GetMapping
    public List<CompanySupervisorDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CompanySupervisorDto getById(int id){
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompanySupervisorDto add(@RequestBody CompanySupervisorDto request){
        return service.add(request);
    }

    @PutMapping
    public CompanySupervisorDto update(@RequestBody CompanySupervisorDto request){
        return service.update(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}