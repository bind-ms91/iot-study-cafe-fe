package bind.iotstudycafe.commons.exampleDomain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDomainSearchCond {

    @NotBlank
    private String name;

    private Integer age;

}
