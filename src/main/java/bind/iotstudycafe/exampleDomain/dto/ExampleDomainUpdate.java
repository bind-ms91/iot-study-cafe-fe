package bind.iotstudycafe.exampleDomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Schema(description = "수정DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDomainUpdate {

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "나이", implementation = Integer.class)
    @NotNull
    @Range(min = 14, max = 200)
    private Integer age;

}
