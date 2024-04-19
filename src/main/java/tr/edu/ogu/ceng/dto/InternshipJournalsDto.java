package tr.edu.ogu.ceng.dto;

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
public class InternshipJournalsDto {
    private Long id;
    private String unitName;
    private String journal;
    private Long operationTime;
    private Timestamp startingDate;
    private Timestamp endDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Long internshipId;
    private Long companySupervisorId;
    private boolean confirmation;
}

