package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.service.FacultySupervisorService;

@RestController

@RequestMapping("/api/facultySupervisor")

public class FactorySupervisorController {

    @Autowired
    private FacultySupervisorService facultySupervisorService;

    @PostMapping("/saveFacultysupervisor")
    public ResponseEntity<FacultySupervisor> addFacultySupervisor (@RequestBody FacultySupervisor facultySupervisor){
        FacultySupervisor facultySupervisor1 = facultySupervisorService.saveFacultySupervisor(facultySupervisor);
        return new ResponseEntity<>(facultySupervisor1, HttpStatus.CREATED);
    }
}
