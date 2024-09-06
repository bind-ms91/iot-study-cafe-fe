package bind.iotstudycafe.member.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Schema(description = "회원 객체")
@Data
public class Member {

    private Long id;        //seq

    private String memberId;
    private String memberPassword;
    private String memberName;
    private Integer age;

    private MemberGrade memberGrade;

    public Member() {
    }

    public Member(String memberId, String memberPassword, String memberName, Integer age, MemberGrade memberGrade) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.age = age;
        this.memberGrade = memberGrade;
    }


    public void setMemberGrade(String memberGrade) {
        if (memberGrade == null) {
            this.memberGrade = MemberGrade.BASIC;
        } else {
            this.memberGrade = MemberGrade.valueOf(memberGrade);
        }
    }

    // Grade를 포함하는지 확인하는 메서드 추가
    public boolean containsGrade(String memberGrade) {
        return this.memberGrade == MemberGrade.valueOf(memberGrade);
    }

}
