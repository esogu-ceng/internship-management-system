package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.model.FacultySupervisor;

import java.time.LocalDateTime;

@Service
public class FacultySupervisorService {

    @Autowired
    private FacultySupervisorRepository facultySupervisorRepository;

    public FacultySupervisor saveFacultySupervisor(FacultySupervisor facultySupervisor){
        LocalDateTime localDateTime = LocalDateTime.now();
        facultySupervisor.setCreateDate(localDateTime);
        facultySupervisor.setUpdateDate(localDateTime);
        return facultySupervisorRepository.save(facultySupervisor);
    }
}
