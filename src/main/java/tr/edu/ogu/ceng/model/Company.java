package tr.edu.ogu.ceng.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "ims_companies")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "fax_number", nullable = false)
    private String faxNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "scope", nullable = false)
    private String scope;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

}
