package io.github.felipe11dias.validation.constraintValidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.felipe11dias.validation.NotEmptyList;

public class NotEmpytListValidator implements ConstraintValidator<NotEmptyList, List> {

	@Override
	public boolean isValid(List list, ConstraintValidatorContext context) {
		return list != null	&& !list.isEmpty();
	}

}
