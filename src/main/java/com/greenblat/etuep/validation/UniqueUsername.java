package com.greenblat.etuep.validation;

import com.greenblat.etuep.validation.impl.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "Username should be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
