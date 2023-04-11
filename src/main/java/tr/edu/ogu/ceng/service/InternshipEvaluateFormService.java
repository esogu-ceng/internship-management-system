package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.InternshipEvaluateFormRepository;
import tr.edu.ogu.ceng.dto.InternshipEvaluateFormDto;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;

@Service
@AllArgsConstructor
public class InternshipEvaluateFormService {
	@Autowired
	private InternshipEvaluateFormRepository internshipEvaluateFormRepository;
	private final InternshipService internshipService;
	private final CompanyService companyService;

	public void formFileUpload(MultipartFile file, InternshipEvaluateFormDto dto) {
		InternshipEvaluateForm internshipEvaluateFormForm = new InternshipEvaluateForm();

		String fileName = new File(file.getOriginalFilename()).getName();
		String filePath = "C:/uploads/" + fileName;

		try {
			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		internshipEvaluateFormForm.setName(dto.getName());
		internshipEvaluateFormForm.setSurname(dto.getSurname());
		internshipEvaluateFormForm.setFilePath(filePath);

		Internship internship = internshipService.getInternship(dto.getInternship().getId()).orElse(null);
		Company company = companyService.getCompany(dto.getCompany().getId());

		if (internship != null && company != null) {
			internshipEvaluateFormForm.setInternship(internship);
			internshipEvaluateFormForm.setCompany(company);
			internshipEvaluateFormRepository.save(internshipEvaluateFormForm);
		} else {
			throw new IllegalArgumentException("Hatalı internship ID veya company ID");
		}
	}

	public InternshipEvaluateForm getInternshipEvaluateFormById(Long id) {
		InternshipEvaluateForm intershipEvaluateForm = internshipEvaluateFormRepository.findById(id).orElse(null);
		return intershipEvaluateForm;
	}

	public List<InternshipEvaluateForm> getAllInternshipEvaluateForms() {
		return internshipEvaluateFormRepository.findAll();
	}

}
