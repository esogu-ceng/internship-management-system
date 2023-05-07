package tr.edu.ogu.ceng.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.enums.InternshipStatus;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipResponseDto {
    private Long id;
    private InternshipStatus status;
    private int days;
    private Long studentId;
    private Long companyId;
    private Long facultySupervisorId;
}
