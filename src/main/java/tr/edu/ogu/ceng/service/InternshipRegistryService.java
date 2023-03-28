package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;

@Service
public class InternshipRegistryService {

    @Autowired
    private InternshipRegistryRepository internshipRegistryRepository;

    public boolean deleteInternshipRegistry(long id){
        return internshipRegistryRepository.deleteInternshipRegistry(id);
    }
}
