package bind.iotstudycafe.commons.exampleDomain.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

@Data
public class ExampleDomain {

    private Long id;

    private String loginId;
    private String password;
    private String name;
    private Integer age;


}
