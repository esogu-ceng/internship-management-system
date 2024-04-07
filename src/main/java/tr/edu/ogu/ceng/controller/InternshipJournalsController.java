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

import tr.edu.ogu.ceng.dto.requests.InternshipJournalsRequestDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipJournalsResponseDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.service.InternshipJournalsService;
import tr.edu.ogu.ceng.util.PageableUtil;


@RestController
@RequestMapping("/api/internshipjournals")
public class InternshipJournalsController {

    @Autowired
    private InternshipJournalsService internshipJournalsService;

    @PostMapping("/")
	public ResponseEntity<InternshipJournalsResponseDto> addInternshipJournal(@RequestBody InternshipJournalsRequestDto internshipJournalsRequestDto) {
        System.out.println(internshipJournalsRequestDto.getStartingDate().getClass().getSimpleName());
		return ResponseEntity.ok(internshipJournalsService.addInternshipJournal(internshipJournalsRequestDto));
	}
}
