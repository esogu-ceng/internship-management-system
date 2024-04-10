package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.lang.model.type.IntersectionType;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.postgresql.util.PGTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipDocumentsRepository;
import tr.edu.ogu.ceng.dao.InternshipJournalsRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.InternshipJournalsDto;
import tr.edu.ogu.ceng.model.InternshipDocument;
import tr.edu.ogu.ceng.model.InternshipJournal;

@Slf4j
@Service
@AllArgsConstructor
@Builder

public class InternshipJournalsService {
    @Autowired
    private InternshipJournalsRepository internshipJournalsRepository;
	private InternshipRepository internshipRepository;
	private final ModelMapper modelMapper;

    public InternshipJournalsDto addInternshipJournal(InternshipJournal internshipJournal){
        try {
            
            
            internshipJournal = internshipJournalsRepository.save(internshipJournal);
            
            log.info("Internship has been saved successfully with id = {}.", internshipJournal.getId());
			return modelMapper.map(internshipJournal,InternshipJournalsDto.class);
            
        } catch (Exception e) {
            log.error("Error occurred while saving internship: {}", e.getMessage());
			throw e;
            
		}
    }


    public List<InternshipJournal> getAllInternshipJournalsByInternshipId(Long internshipId) {
        List<InternshipJournal> internshipJournals = internshipJournalsRepository.findAllJournalByInternshipId(internshipId);
        if (internshipJournals.isEmpty()) {
            log.warn("The internship documents list is empty.");
        }
        return internshipJournals;
    }
    


   
    public InternshipJournal getInternshipJournalById(Long journalId) {
        return internshipJournalsRepository.findById(journalId).orElse(null);
    }

    public InternshipJournal updateInternshipJournal(InternshipJournal internshipJournal) {
        return internshipJournalsRepository.save(internshipJournal);
    }
    
}
