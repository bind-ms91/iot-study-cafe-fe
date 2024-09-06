package bind.iotstudycafe.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum MemberGrade {

    OPERATOR("ROLE_OPERATOR"),
    OWNER("ROLE_OWNER"),
    BASIC("ROLE_BASIC"),;

    private String role;
}
