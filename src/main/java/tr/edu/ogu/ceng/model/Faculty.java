package tr.edu.ogu.ceng.model;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ims_faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

}