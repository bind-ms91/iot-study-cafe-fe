package bind.iotstudycafe.commons.exampleDomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Schema(description = "저장DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDomainSave {

    @Schema(description = "로그인 ID")
    @NotBlank
    private String loginId;

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "나이")
    @NotNull
    @Range(min = 14, max = 200)
    private Integer age;

}
