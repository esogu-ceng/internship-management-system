package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

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

    public Internship() {
    }
    
    // constructor with parameters
    public Internship(String status, Timestamp startDate, Timestamp endDate, int days, Timestamp createDate, Timestamp updateDate, Student student, Company company, FacultySupervisor facultySupervisor) {
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.student = student;
        this.company = company;
        this.facultySupervisor = facultySupervisor;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public FacultySupervisor getFacultySupervisor() {
        return facultySupervisor;
    }

    public void setFacultySupervisor(FacultySupervisor facultySupervisor) {
        this.facultySupervisor = facultySupervisor;
    }
}
