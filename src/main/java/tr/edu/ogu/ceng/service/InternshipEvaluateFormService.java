package tr.edu.ogu.ceng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.InternshipEvaluateFormRepository;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;

@Service
@AllArgsConstructor
public class InternshipEvaluateFormService {
	@Autowired
	private InternshipEvaluateFormRepository internshipEvaluateFormRepository;

	public InternshipEvaluateForm createInternshipEvaluateForm(InternshipEvaluateForm internshipEvaluateForm) {
		return internshipEvaluateFormRepository.save(internshipEvaluateForm);
	}

	public InternshipEvaluateForm getInternshipEvaluateFormById(Long id) {
		InternshipEvaluateForm intershipEvaluateForm = internshipEvaluateFormRepository.findById(id).orElse(null);
		return intershipEvaluateForm;
	}

	public List<InternshipEvaluateForm> getAllInternshipEvaluateForms() {
		return internshipEvaluateFormRepository.findAll();
	}

}
