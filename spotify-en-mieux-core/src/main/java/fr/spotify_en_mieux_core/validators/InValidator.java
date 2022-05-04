package fr.spotify_en_mieux_core.validators;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InValidator implements ConstraintValidator<In, String> {

	private String[] authorizedValues;
	
	
	@Override
	public void initialize(In constraintAnnotation) {
		authorizedValues = constraintAnnotation.authorizedValues();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Arrays.asList(authorizedValues).contains(value);
	}

}
