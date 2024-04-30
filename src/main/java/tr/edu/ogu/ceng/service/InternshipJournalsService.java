package tr.edu.ogu.ceng.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.InternshipJournalsRepository;
import tr.edu.ogu.ceng.dto.InternshipJournalsDto;
import tr.edu.ogu.ceng.model.InternshipJournal;

@Slf4j
@Service
@AllArgsConstructor
public class InternshipJournalsService {
	private InternshipJournalsRepository internshipJournalsRepository;
	private final ModelMapper modelMapper;

	public InternshipJournalsDto addInternshipJournal(InternshipJournal internshipJournal) {
		try {

			internshipJournal = internshipJournalsRepository.save(internshipJournal);

			log.info("Internship has been saved successfully with id = {}.", internshipJournal.getId());
			return modelMapper.map(internshipJournal, InternshipJournalsDto.class);

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
