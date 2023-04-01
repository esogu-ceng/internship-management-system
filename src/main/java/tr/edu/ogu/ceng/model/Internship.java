package tr.edu.ogu.ceng.model;


import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@Table(name = "ims_internships")
@Data

public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;
    
    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;
    
    @Column(name = "days", nullable = false)
    private int days;
    
    @Column(name = "create_date")
    private Timestamp createDate;
    
    @Column(name = "update_date")
    private Timestamp updateDate;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_supervisor_id", nullable = false)
    private FacultySupervisor facultySupervisor;
    
}
