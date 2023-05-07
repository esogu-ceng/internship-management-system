package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultySupervisorRequestDto {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String supervisorNo;
    private UserRequestDto user;
    private FacultyRequestDto faculty;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
