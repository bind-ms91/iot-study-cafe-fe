package bind.iotstudycafe.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {

    private String memberPassword;
    private String memberName;
    private int age;
    private String memberGrade;

}
