package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipDocumentsRepository;
import tr.edu.ogu.ceng.dto.responses.InternshipDocumentsResponseDto;
import tr.edu.ogu.ceng.model.InternshipDocument;

@AllArgsConstructor
@Slf4j
@Service
public class InternshipDocumentsService {

	private InternshipDocumentsRepository internshipDocumentsRepository;

	public Page<InternshipDocumentsResponseDto> getAllInternshipDocumentsByInternshipId(Long internshipId,
			Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Page<InternshipDocument> internshipDocuments = internshipDocumentsRepository
					.findAllDocumentByInternshipId(internshipId, pageable);
			if (internshipDocuments.isEmpty()) {
				log.warn("The internship documents list is empty.");
			}
			Page<InternshipDocumentsResponseDto> internshipDocumentsDtos = internshipDocuments.map(
					internshipDocument -> modelMapper.map(internshipDocument, InternshipDocumentsResponseDto.class));
			log.info("Internship documents retrieved successfully.");
			return internshipDocumentsDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting internship documents: {}", e.getMessage());
			throw e;
		}
	}

	public byte[] download(Long fileId) throws IOException {
		try {
			InternshipDocument internshipDocument = internshipDocumentsRepository.getReferenceById(fileId);

			File file = new File(internshipDocument.getFilePath());

			// Dosyanın byte içeriğini oku
			byte[] fileContent = Files.readAllBytes(file.toPath());

			return fileContent;
		} catch (Exception e) {
			throw new IOException("An error occurred while getting internship documents: " + e.getMessage());
		}
	}
}