package tr.edu.ogu.ceng.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import tr.edu.ogu.ceng.dto.requests.InternshipDocumentRequestDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipDocumentResponseDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.service.InternshipDocumentsService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/internshipdocument")
public class InternshipDocumentsController {

    @Autowired
    private InternshipDocumentsService internshipDocumentsService;
  

    @GetMapping("/internship/{id}")
    public Page<InternshipDocumentResponseDto> getAllInternshipDocuments(
            @PathVariable(name = "id") Long internshipId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
        Page<InternshipDocumentResponseDto> internshipDocuments = internshipDocumentsService
                .getAllInternshipDocumentsByInternshipId(internshipId, pageable);
        return internshipDocuments;
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "filePath") String filepath) throws IOException {
        return internshipDocumentsService.download(filepath);
    }

    @PostMapping("/upload")
	public ResponseEntity<InternshipDocumentResponseDto> addInternshipJournalDocument(@RequestBody InternshipDocumentRequestDto internshipDocumentRequestDto) {
		return ResponseEntity.ok(internshipDocumentsService.addInternshipJournalDocument(internshipDocumentRequestDto));
	}

    
}
