package tr.edu.ogu.ceng.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

}
