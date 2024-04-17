package tr.edu.ogu.ceng.dto.requests;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipJournalsRequestDto {
    private String unitName;
    private String journal;
    private Long operationTime;
    private Timestamp startingDate;
    private Timestamp endDate;
    private Long internshipId;
    private Long companySupervisorId;
}

  