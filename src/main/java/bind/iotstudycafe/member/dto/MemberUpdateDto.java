package bind.iotstudycafe.member.dto;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private String memberPassword;
    private String memberName;
    private int age;
    private String memberGrade;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String memberPassword, String memberName, int age, String memberGrade) {
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.age = age;
        this.memberGrade = memberGrade;
    }
}
