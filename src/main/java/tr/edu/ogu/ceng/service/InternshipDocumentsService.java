package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.modelmapper.ModelMapper;
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
import tr.edu.ogu.ceng.dto.responses.InternshipDocumentsResponseDto;
import tr.edu.ogu.ceng.model.InternshipDocument;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InternshipDocumentsService {

	@Autowired
	private InternshipDocumentsRepository internshipDocumentsRepository;

	public Page<InternshipDocumentsResponseDto> getAllInternshipDocumentsByInternshipId(Long internshipId, Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting internship documents by id: {} with pageable: {}", internshipId, pageable);
			Page<InternshipDocument> internshipDocuments = internshipDocumentsRepository.findAllDocumentByInternshipId(internshipId, pageable);
			if (internshipDocuments.isEmpty()) {
				log.warn("The internship documents list is empty.");
			}
			Page<InternshipDocumentsResponseDto> internshipDocumentsDtos = internshipDocuments.map(
					internshipDocument -> modelMapper.map(internshipDocument, InternshipDocumentsResponseDto.class));
			return internshipDocumentsDtos;
		} catch (Exception e) {
			log.error("An error occured while getting internship documents: {}", e.getMessage());
			throw e;
		}
	}

	@RequestMapping(path = "/download", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> download(String filepath) throws IOException {
		try {
			File file2Upload = new File(filepath);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file2Upload));

			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(file2Upload.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(resource);
		} catch (Exception e) {
			log.error("An error occured while getting internship documents: {}", e.getMessage());
			throw e;
		}
	}
}
