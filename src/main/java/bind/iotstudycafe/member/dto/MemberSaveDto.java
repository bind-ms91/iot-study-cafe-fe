package bind.iotstudycafe.member.dto;

import bind.iotstudycafe.commons.enumvalidator.EnumValue;
import bind.iotstudycafe.member.domain.MemberGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDto {

    @Schema(description = "로그인 ID")
    @NotBlank
    private String memberId;

    @Schema(description = "비밀번호")
    @NotBlank
    private String memberPassword;

    @Schema(description = "비밀번호 확인")
    @NotBlank
    private String memberPasswordCheck;

    @Schema(description = "이름")
    @NotBlank
    private String memberName;

    @Schema(description = "나이", implementation = Integer.class)
    @NotNull
    @Range(min = 14, max = 200)
    private Integer age;

    @EnumValue(enumClass = MemberGrade.class, ignoreCase = true)
    private String memberGrade;

}
