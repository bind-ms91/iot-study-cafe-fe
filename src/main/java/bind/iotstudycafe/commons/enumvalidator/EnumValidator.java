package bind.iotstudycafe.commons.enumvalidator;

import bind.iotstudycafe.member.domain.MemberGrade;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue enumValue;

    // 애노테이션의 속성을 초기화합니다.
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumValue = constraintAnnotation;
    }

    // String 값이 유효한 ENUM 값인지 확인합니다.
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final Enum<?>[] enumConstants = this.enumValue.enumClass().getEnumConstants();

        // null 허용 여부 확인
        if (value == null) {
            return enumValue.allowNull();
        }

        if (enumConstants == null) {
            return false;
        }

        boolean isValid  = Arrays.stream(enumConstants)
                .anyMatch(enumConstant -> convertible(value, enumConstant) || convertibleIgnoreCase(value, enumConstant));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(getErrorMessage(enumValue.enumClass()))
                    .addConstraintViolation();
        }

        return isValid;
    }

    private boolean convertibleIgnoreCase(String value, Enum<?> enumConstant) {
        return this.enumValue.ignoreCase() && value.trim().equalsIgnoreCase(enumConstant.name());

    }

    private boolean convertible(String value, Enum<?> enumConstant) {
        return value.trim().equals(enumConstant.name());
    }

    private String getErrorMessage(Class<? extends Enum<?>> enumClass) {
        if (enumClass == MemberGrade.class) {
            return EnumValidatorMessage.MEMBER_GRADE.getMessage();
        } else {
            return "유효하지 않은 값입니다.";
        }
    }
}
