package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;
import javax.persistence.*;

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
    private Long id;

    @Column(name = "file_path", nullable = false)
    private String file_path;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "create_date", nullable = true)
    private Timestamp create_date;

    @Column(name = "update_date", nullable = true)
    private Timestamp update_date;

    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;
}
