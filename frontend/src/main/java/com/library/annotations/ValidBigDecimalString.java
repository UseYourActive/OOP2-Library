package com.library.annotations;

import java.lang.annotation.*;

@Documented
//@Constraint(validatedBy = BigDecimalStringValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBigDecimalString {
    String message() default "Invalid BigDecimal format";
    Class<?>[] groups() default {};
    //Class<? extends Payload>[] payload() default {};
}
