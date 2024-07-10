package bind.iotstudycafe.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDto {

    private String memberId;
    private String memberPassword;
    private String memberName;
    private Integer age;
    private String memberGrade;

}
