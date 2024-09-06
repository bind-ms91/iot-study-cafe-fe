package bind.iotstudycafe.commons.enumvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValue {

    // 검증할 ENUM 클래스
    Class<? extends Enum<?>> enumClass();

    // 기본 검증 실패 메시지
    String message() default "유효하지 않은 값입니다.";;

    // Bean Validation의 기본 그룹
    Class<?>[] groups() default {};

    // Bean Validation의 기본 페이로드
    Class<? extends Payload>[] payload() default {};

    // null 체크 옵션
    boolean allowNull() default true;

    // 대소문자 무시 옵션
    boolean ignoreCase() default false;

}
