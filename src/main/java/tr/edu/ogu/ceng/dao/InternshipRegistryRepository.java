package tr.edu.ogu.ceng.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InternshipRegistryRepository {

    @Autowired
    private InternshipRegistryJpaRepositoryInterface repository;

    public boolean deleteInternshipRegistry(long id){
        if (!repository.existsById(id)){
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
