package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.InternshipEvaluateFormRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dto.InternshipEvaluateFormDto;
import tr.edu.ogu.ceng.dto.requests.InternshipEvaluateFormRequestDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;
import tr.edu.ogu.ceng.model.Setting;

@Slf4j
@Service
@AllArgsConstructor
public class InternshipEvaluateFormService {
    @Autowired
    private InternshipEvaluateFormRepository internshipEvaluateFormRepository;
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final SettingRepository settingsRepository;
    private final ModelMapper modelMapper;

    public InternshipEvaluateForm formFileUpload(MultipartFile file, InternshipEvaluateFormDto dto) {
        InternshipEvaluateForm internshipEvaluateForm = new InternshipEvaluateForm();
        String key = "upload_directory";
        Setting setting = settingsRepository.findByKey(key);
        String fileName = new File(file.getOriginalFilename()).getName();
        String filePath = setting.getValue() + "\\" + fileName;

        try {
            log.info("File upload started");
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("File upload failed");
            e.printStackTrace();
        }

        internshipEvaluateForm.setName(dto.getName());
        internshipEvaluateForm.setSurname(dto.getSurname());
        internshipEvaluateForm.setFilePath(filePath);

        Internship internship = internshipRepository.findById(dto.getInternship().getId()).orElse(null);
        Company company = companyRepository.findById(dto.getCompany().getId()).orElse(null);

        if (internship != null && company != null) {
            internshipEvaluateForm.setInternship(internship);
            internshipEvaluateForm.setCompany(company);
            internshipEvaluateFormRepository.save(internshipEvaluateForm);
            log.info("File upload completed");
        } else {
            log.error("Wrong internship ID or company ID");
            throw new IllegalArgumentException("Hatalı internship ID veya company ID");
        }
        return internshipEvaluateForm;
    }

    public InternshipEvaluateForm getInternshipEvaluateFormById(Long id) {
        log.info("Getting internship evaluate form by id: {}", id);
        return internshipEvaluateFormRepository.findById(id).orElse(null);
    }

    public Page<InternshipEvaluateForm> getAllInternshipEvaluateForms(Pageable pageable) {
        log.info("Getting all internship evaluate forms");
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
