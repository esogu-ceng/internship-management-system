package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import tr.edu.ogu.ceng.model.InternshipRegistry;
import tr.edu.ogu.ceng.service.InternshipRegistryService;

@RestController
@RequestMapping("/api/internshipregistry")
public class InternshipRegistryController {

    @Autowired
    private InternshipRegistryService internshipRegistryService;

    @DeleteMapping("/{id}")
    public boolean deleteInternshipRegistry(@PathVariable(name = "id") long id){
        return internshipRegistryService.deleteInternshipRegistry(id);
    }
    
    @PutMapping
	public ResponseEntity<InternshipRegistry> updateInternshipRegistry(@RequestBody InternshipRegistry internshipRegistry) {
		return ResponseEntity.ok(internshipRegistryService.updateInternshipRegistry(internshipRegistry));
	}
    
    @PostMapping
	public ResponseEntity<InternshipRegistry> addInternshipRegistries(@RequestBody InternshipRegistry internshipRegistries) {
		return ResponseEntity.ok(internshipRegistryService.addInternshipRegistry(internshipRegistries));
	}
    @GetMapping("/{userId}")
    public ResponseEntity<Page<InternshipRegistry>> listAllInternshipRegistiries(@PathVariable Long userId,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<InternshipRegistry> internshipRegistries = internshipRegistryService.findAllRegistiriesByUserId(userId, pageRequest);

        return ResponseEntity.ok(internshipRegistries);
    }

    
}
