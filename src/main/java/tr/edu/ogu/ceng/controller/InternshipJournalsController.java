package tr.edu.ogu.ceng.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.java.Log;

import tr.edu.ogu.ceng.dto.InternshipJournalsDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.dto.responses.InternshipDocumentsResponseDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.model.InternshipJournal;
import tr.edu.ogu.ceng.service.InternshipJournalsService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/internshipjournals")
public class InternshipJournalsController {

    @Autowired
    private InternshipJournalsService internshipJournalsService;
    private final ModelMapper modelMapper = new ModelMapper();    

    @PostMapping("/")
	public ResponseEntity<InternshipJournalsDto> addInternshipJournal(@RequestBody InternshipJournalsDto internshipJournalsDto) {
        
        InternshipJournal internshipJournal = modelMapper.map(internshipJournalsDto, InternshipJournal.class);
       
        LocalDateTime dateTime = LocalDateTime.now();
        internshipJournal.setCreateDate(dateTime);
		internshipJournal.setUpdateDate(dateTime);
        internshipJournal.setConfirmation(false);
        
		return ResponseEntity.ok(internshipJournalsService.addInternshipJournal(modelMapper.map(internshipJournal,InternshipJournal.class)));
            
        
        

		
	}

    @GetMapping("/internship/{id}")
public ResponseEntity<List<InternshipJournalsDto>> getAllInternshipJournals(
    @PathVariable(name = "id") Long internshipId,
    @RequestParam(defaultValue = "id") String sortBy)
{
    ModelMapper modelMapper = new ModelMapper();

    List<InternshipJournal> internshipJournals = internshipJournalsService.getAllInternshipJournalsByInternshipId(internshipId);

    List<InternshipJournalsDto> internshipJournalDtos = internshipJournals.stream()
        .map(internshipJournal -> modelMapper.map(internshipJournal, InternshipJournalsDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.ok(internshipJournalDtos);
}

    @PutMapping("/confirm/{journalId}")
    public ResponseEntity<InternshipJournal> confirmInternshipJournal(@PathVariable(name = "journalId") Long journalId) {
      
        InternshipJournal internshipJournal = internshipJournalsService.getInternshipJournalById(journalId);

        if (internshipJournal == null) {
            return ResponseEntity.notFound().build(); 
        }

 
        internshipJournal.setConfirmation(true);

        
        internshipJournal = internshipJournalsService.updateInternshipJournal(internshipJournal);

        return ResponseEntity.ok(internshipJournal); 
    }

    @PutMapping("/confirm-all/{internshipId}")
    public ResponseEntity<String> confirmAllInternshipJournals(@PathVariable(name = "internshipId") Long internshipId) {
        List<InternshipJournal> internshipJournals = internshipJournalsService.getAllInternshipJournalsByInternshipId(internshipId);

        if (internshipJournals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No internship journals found for the provided internship ID.");
        }

        for (InternshipJournal journal : internshipJournals) {
            journal.setConfirmation(true);
            internshipJournalsService.updateInternshipJournal(journal);
        }

        return ResponseEntity.ok().body("{\"message\": \"All internship journals have been confirmed successfully.\"}");

    }
}
