package io.github.felipe11dias.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import io.github.felipe11dias.validation.constraintValidation.NotEmpytListValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmpytListValidator.class)
public @interface NotEmptyList {

	String message() default "A lista não pode ser vazia.";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
