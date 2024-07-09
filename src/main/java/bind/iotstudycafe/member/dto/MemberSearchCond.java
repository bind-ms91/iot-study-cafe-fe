package bind.iotstudycafe.member.dto;

import lombok.Data;

@Data
public class MemberSearchCond {

    private String memberId;
    private String memberName;
    private Integer maxAge;
    private Integer minAge;
    private String memberGrade;

    public MemberSearchCond() {
    }

    public MemberSearchCond(String memberId, String memberName, Integer maxAge, Integer minAge, String memberGrade) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.memberGrade = memberGrade;
    }
}
