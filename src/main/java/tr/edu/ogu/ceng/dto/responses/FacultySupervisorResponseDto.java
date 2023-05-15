package tr.edu.ogu.ceng.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultySupervisorResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String supervisorNo;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Long userId;
    private Long facultyId;
}
