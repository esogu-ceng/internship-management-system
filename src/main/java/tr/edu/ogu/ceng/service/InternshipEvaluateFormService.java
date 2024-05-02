package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.InternshipEvaluateFormRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dto.InternshipEvaluateFormDto;
import tr.edu.ogu.ceng.dto.requests.InternshipEvaluateFormRequestDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class InternshipEvaluateFormService {

	private InternshipEvaluateFormRepository internshipEvaluateFormRepository;
	private final InternshipRepository internshipRepository;
	private final CompanyRepository companyRepository;
	private final SettingRepository settingsRepository;
	private final ModelMapper modelMapper;
	private MessageResource messageResource;
	public InternshipEvaluateFormDto saveInternshipEvalForm(InternshipEvaluateFormDto formDto) {
		InternshipEvaluateForm existingForm = internshipEvaluateFormRepository.findById(formDto.getId())
				.orElseThrow(() -> new EntityNotFoundException("Form not found with id: " + formDto.getId()));
		existingForm.setId(formDto.getId());
		existingForm.setInternship(formDto.getInternship());
		existingForm.setCompany(formDto.getCompany());
		existingForm.setQuestion1(formDto.getQuestion1());
		existingForm.setQuestion2(formDto.getQuestion2());
		existingForm.setQuestion3(formDto.getQuestion3());
		existingForm.setQuestion4(formDto.getQuestion4());
		existingForm.setQuestion5(formDto.getQuestion5());
		existingForm.setQuestion6(formDto.getQuestion6());
		existingForm.setQuestion7(formDto.getQuestion7());
		existingForm.setQuestion8(formDto.getQuestion8());

		InternshipEvaluateForm savedForm = internshipEvaluateFormRepository.save(existingForm);
		return mapEntityToDto(savedForm);
	}
	public InternshipEvaluateFormDto mapEntityToDto(InternshipEvaluateForm form) {
		InternshipEvaluateFormDto dto = new InternshipEvaluateFormDto();
		dto.setId(form.getId());
		dto.setInternship(form.getInternship());
		dto.setCompany(form.getCompany());
		dto.setQuestion1(form.getQuestion1());
		dto.setQuestion2(form.getQuestion2());
		dto.setQuestion3(form.getQuestion3());
		dto.setQuestion4(form.getQuestion4());
		dto.setQuestion5(form.getQuestion5());
		dto.setQuestion6(form.getQuestion6());
		dto.setQuestion7(form.getQuestion7());
		dto.setQuestion8(form.getQuestion8());
		return dto;
	}

	public InternshipEvaluateForm formFileUpload(MultipartFile file, InternshipEvaluateFormDto dto) {
		InternshipEvaluateForm internshipEvaluateForm = new InternshipEvaluateForm();
		String key = "upload_directory";
		Setting setting = settingsRepository.findByKey(key);
		String fileName = new File(file.getOriginalFilename()).getName();
		String filePath = setting.getValue() + "\\" + fileName;

		try {
			log.info("File upload started: {}", fileName);
			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error("An error occurred while uploading file: {}", e.getMessage());
			e.printStackTrace();
		}
		Internship internship = internshipRepository.findById(dto.getInternship().getId()).orElse(null);
		Company company = companyRepository.findById(dto.getCompany().getId()).orElse(null);

		if (internship != null && company != null) {
			internshipEvaluateForm.setInternship(internship);
			internshipEvaluateForm.setCompany(company);
			internshipEvaluateFormRepository.save(internshipEvaluateForm);
			log.info("Internship evaluate form saved successfully with id: {}", internshipEvaluateForm.getId());
		} else {
			log.error("Wrong internship ID : {} or company ID : {}", dto.getInternship().getId(), dto.getCompany().getId());
			throw new IllegalArgumentException(messageResource.getMessage("internship.or.company.id.wrong"));
		}
		return internshipEvaluateForm;
	}

	public InternshipEvaluateForm getInternshipEvaluateFormById(Long id) {
		log.info("Getting internship evaluate form by id: {}", id);
		return internshipEvaluateFormRepository.findById(id).orElse(null);
	}

	public Page<InternshipEvaluateForm> getAllInternshipEvaluateForms(Pageable pageable) {

		log.info("Getting internship evaluate forms with page number: {}", pageable.getPageNumber());

		return internshipEvaluateFormRepository.findAll(pageable);
	}

	public InternshipEvaluateForm getByInternshipId(Long internshipId) {
		log.info("Getting internship evaluate form by internship id: {}", internshipId);
		return internshipEvaluateFormRepository.findByInternshipId(internshipId);
	}

	public InternshipEvaluateFormDto addInternshipEvaluateForm(InternshipEvaluateFormRequestDto internshipEvaluateFormRequestDto) {
		InternshipEvaluateForm internshipEvaluateForm = modelMapper.map(internshipEvaluateFormRequestDto, InternshipEvaluateForm.class);
		try {
			internshipEvaluateForm = internshipEvaluateFormRepository.save(internshipEvaluateForm);
			log.info("Internship evaluate form saved successfully with id: {}", internshipEvaluateForm.getId());
			return modelMapper.map(internshipEvaluateForm, InternshipEvaluateFormDto.class);
		} catch (Exception e) {
			log.error("An error occurred while saving internship evaluate form: {}", e.getMessage());
			throw e;
		}
	}

	public InternshipEvaluateFormDto updateInternshipEvaluateForm(InternshipEvaluateFormRequestDto internshipEvaluateFormRequestDto) {
		InternshipEvaluateForm internshipEvaluateForm = internshipEvaluateFormRepository.findById(internshipEvaluateFormRequestDto.getId()).orElse(null);
		if (internshipEvaluateForm == null) {
			log.warn("No internship evaluate form found with id: {}", internshipEvaluateFormRequestDto.getId());
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
		InternshipEvaluateForm internshipEvaluateFormMapped = modelMapper.map(internshipEvaluateFormRequestDto, InternshipEvaluateForm.class);

		internshipEvaluateForm = internshipEvaluateFormRepository.save(internshipEvaluateFormMapped);
		log.info("Internship evaluate form updated successfully with id: {}", internshipEvaluateForm.getId());
		return modelMapper.map(internshipEvaluateForm, InternshipEvaluateFormDto.class);
	}

}
