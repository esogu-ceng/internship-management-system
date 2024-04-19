package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.nio.file.Files;

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

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import tr.edu.ogu.ceng.dao.InternshipDocumentsRepository;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Service
public class InternshipDocumentsService {

	@Autowired
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