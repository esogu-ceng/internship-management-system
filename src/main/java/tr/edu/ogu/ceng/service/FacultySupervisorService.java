package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
public class FacultySupervisorService {

    @Autowired
    private FacultySupervisorRepository facultySupervisorRepository;

    public FacultySupervisor saveFacultySupervisor(FacultySupervisor facultySupervisor){
    	Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
        facultySupervisor.setCreateDate(localDateTime);
        facultySupervisor.setUpdateDate(localDateTime);
        return facultySupervisorRepository.save(facultySupervisor);
    }

    public FacultySupervisor updateFacultySupervisor(FacultySupervisor facultySupervisor){
        if(!facultySupervisorRepository.existsById(facultySupervisor.getId())) throw new EntityNotFoundException("Faculty supervisor not found!");
        Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
        facultySupervisor.setUpdateDate(localDateTime);
        return facultySupervisorRepository.save(facultySupervisor);
    }

    public Optional<FacultySupervisor> getFacultySupervisor(Long id) {
        if (!facultySupervisorRepository.existsById(id)) throw new EntityNotFoundException("Faculty supervisor not found!");
        return facultySupervisorRepository.findById(id);
    }
}
