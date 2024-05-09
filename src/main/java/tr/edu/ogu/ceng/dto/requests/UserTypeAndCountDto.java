package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeAndCountDto {
    private String user_type;
    private Long count;

    public void translateUserType() {
        switch (user_type) {
            case "ADMIN":
                user_type = "Yönetici";
                break;
            case "STUDENT":
                user_type = "Öğrenci";
                break;
            case "COMPANYSUPERVISOR":
                user_type = "Şirket Denetmeni";
                break;
            case "FACULTYSUPERVISOR":
                user_type = "Fakülte Denetmeni";
                break;
            default:
                break;
        }}

}
