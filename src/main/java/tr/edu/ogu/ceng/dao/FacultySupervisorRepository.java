package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.ceng.model.FacultySupervisor;

public interface FacultySupervisorRepository extends JpaRepository<FacultySupervisor,Long> {
    Page<FacultySupervisor> listAllFacultySupervisors(Pageable pageable);
}
