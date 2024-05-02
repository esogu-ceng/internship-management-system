package tr.edu.ogu.ceng.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.responses.InternshipDocumentsResponseDto;
import tr.edu.ogu.ceng.service.InternshipDocumentsService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/internshipdocument")
@AllArgsConstructor
public class InternshipDocumentsController {

	private InternshipDocumentsService internshipDocumentsService;

	@GetMapping("/internship/{id}")
	public Page<InternshipDocumentsResponseDto> getAllInternshipDocuments(@PathVariable(name = "id") Long internshipId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipDocumentsResponseDto> internshipDocuments = internshipDocumentsService
				.getAllInternshipDocumentsByInternshipId(internshipId, pageable);
		return internshipDocuments;
	}

	@GetMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam(name = "fileId") Long fileId) throws IOException {

		byte[] fileContent = internshipDocumentsService.download(fileId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(fileContent.length)
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileContent);

	}
}