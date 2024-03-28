package tr.edu.ogu.ceng.service;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

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
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipDocumentsRepository;
import tr.edu.ogu.ceng.dto.responses.InternshipDocumentResponseDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipDocument;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.requests.InternshipDocumentRequestDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;

@Slf4j
@Service
@AllArgsConstructor
@Builder

public class InternshipDocumentsService {

	@Autowired
	private InternshipDocumentsRepository internshipDocumentsRepository;
	private InternshipRepository internshipRepository;
	private final ModelMapper modelMapper;

	public Page<InternshipDocumentResponseDto> getAllInternshipDocumentsByInternshipId(Long internshipId, Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Page<InternshipDocument> internshipDocuments = internshipDocumentsRepository.findAllDocumentByInternshipId(internshipId, pageable);
			if (internshipDocuments.isEmpty()) {
				log.warn("The internship documents list is empty.");
			}
			Page<InternshipDocumentResponseDto> internshipDocumentsDtos = internshipDocuments.map(
					internshipDocument -> modelMapper.map(internshipDocument, InternshipDocumentResponseDto.class));
			log.info("Internship documents retrieved successfully.");
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
			log.info("Internship document downloaded successfully. Document name is: {}", file2Upload.getName());
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

	public InternshipDocumentResponseDto addInternshipJournalDocument(InternshipDocumentRequestDto internshipDocumentRequestDto) {
		InternshipDocument internshipDocument = modelMapper.map(internshipDocumentRequestDto, InternshipDocument.class);
		try {
			LocalDateTime dateTime = LocalDateTime.now();
			internshipDocument.setCreateDate(dateTime);
			internshipDocument.setUpdateDate(dateTime);
			internshipDocument.setType("Intership Journal");
			internshipDocument = internshipDocumentsRepository.save(internshipDocument);

			log.info("Internship has been saved successfully with id = {}.", internshipDocument.getId());
			return modelMapper.map(internshipDocument, InternshipDocumentResponseDto.class);
		} catch (Exception e) {
			log.error("Error occurred while saving internship: {}", e.getMessage());
			throw e;
		}
	}
	
}
