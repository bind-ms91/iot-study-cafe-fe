package bind.iotstudycafe.commons.exampleDomain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDomainDto {

    private String loginId;
    private String password;
    private String name;
    private Integer age;

}
