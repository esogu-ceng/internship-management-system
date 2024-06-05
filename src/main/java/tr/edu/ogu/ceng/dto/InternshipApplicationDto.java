package tr.edu.ogu.ceng.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipApplicationDto {
    private Long id;
    private Long studentId;
    private Long companyId;
    private LocalDateTime applicationDate;
}
