package tr.edu.ogu.ceng.dto.responses;

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
public class InternshipJournalsResponseDto {
    private Long id;
    private String unitName;
    private String journal;
    private Long operationTime;
    private Timestamp startingDate;
    private Timestamp endDate;
    private LocalDateTime updateDate;
    private LocalDateTime createDate;
    private Long internshipId;
    private Long companySupervisorId;
    private Integer confirmation;
}
