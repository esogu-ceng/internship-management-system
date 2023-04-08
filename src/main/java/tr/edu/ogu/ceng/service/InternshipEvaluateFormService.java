package tr.edu.ogu.ceng.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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

	public InternshipEvaluateForm updateInternshipEvaluateForm(Long id, InternshipEvaluateForm updatedForm) {
		if (!internshipEvaluateFormRepository.existsById(id))
			throw new EntityNotFoundException("Internship Evaluate Form not found!");
		InternshipEvaluateForm form = getInternshipEvaluateFormById(id);
		form.setName(updatedForm.getName());
		form.setSurname(updatedForm.getSurname());
		form.setFilePath(updatedForm.getFilePath());
		form.setInternship(updatedForm.getInternship());
		form.setCompany(updatedForm.getCompany());
		return internshipEvaluateFormRepository.save(form);
	}

	public boolean deleteInternshipEvaluateForm(Long id) {
		if (!internshipEvaluateFormRepository.existsById(id))
			return false;
		internshipEvaluateFormRepository.deleteById(id);
		return true;
	}
}
