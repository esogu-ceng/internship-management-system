package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ims_internship_registries")
public class InternshipRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "create_date", nullable = true)
    private Timestamp createDate;

    @Column(name = "update_date", nullable = true)
    private Timestamp updateDate;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

}
