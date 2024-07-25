package bind.iotstudycafe.exampleDomain.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "예제 객체")
@Data
public class ExampleDomain {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "로그인 ID")
    private String loginId;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "나이")
    private Integer age;


}
