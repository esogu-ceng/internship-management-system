package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tr.edu.ogu.ceng.service.InternshipRegistryService;

@RestController
@RequestMapping("/api/internshipRegistry")
public class InternshipRegistryController {

    @Autowired
    private InternshipRegistryService internshipRegistryService;

    @DeleteMapping("/{id}")
    public boolean deleteInternshipRegistry(@PathVariable(name = "id") long id){
        return internshipRegistryService.deleteInternshipRegistry(id);
    }
}
