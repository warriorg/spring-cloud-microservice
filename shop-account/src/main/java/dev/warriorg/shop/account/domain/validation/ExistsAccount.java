package dev.warriorg.shop.account.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = AccountValidation.ExistsAccountValidator.class)
public @interface ExistsAccount {

    String message() default "用户不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}