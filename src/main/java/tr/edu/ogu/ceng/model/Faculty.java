package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ims_faculties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faculty {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "create_date")
    private Timestamp createDate;
    
    @Column(name = "update_date")
    private Timestamp updateDate;

}