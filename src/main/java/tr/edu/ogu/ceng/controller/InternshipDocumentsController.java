package tr.edu.ogu.ceng.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.responses.InternshipDocumentsResponseDto;
import tr.edu.ogu.ceng.service.InternshipDocumentsService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/internshipdocument")
public class InternshipDocumentsController {

	@Autowired
	private InternshipDocumentsService internshipDocumentsService;

	@GetMapping("/internship/{id}")
	public Page<InternshipDocumentsResponseDto> getAllInternshipDocuments(
			@PathVariable(name = "id") Long internshipId,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipDocumentsResponseDto> internshipDocuments = internshipDocumentsService
				.getAllInternshipDocumentsByInternshipId(internshipId, pageable);
		return internshipDocuments;
	}

	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> download(@RequestParam(name = "filePath") String filepath) throws IOException {
		return internshipDocumentsService.download(filepath);
	}
}