package bind.iotstudycafe.commons.enumvalidator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumValidatorMessage {

    MEMBER_GRADE("유효하지 않은 회원 등급입니다.");
    
    private final String message;

}
