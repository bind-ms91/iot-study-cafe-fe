package bind.iotstudycafe.exampleDomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Schema(description = "조회DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDomainSearchCond {

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "나이")
    private Integer age;

}
