package bind.iotstudycafe.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchCond {

    private String memberId;
    private String memberName;
    private Integer maxAge;
    private Integer minAge;
    private String memberGrade;

}
