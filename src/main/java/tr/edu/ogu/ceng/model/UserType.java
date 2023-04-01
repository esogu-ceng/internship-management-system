package tr.edu.ogu.ceng.model;

import javax.persistence.*;

import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "ims_user_types")
@Data
public class UserType {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

}