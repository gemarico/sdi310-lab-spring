package com.uniovi.validators;

import com.uniovi.entities.Mark;
import com.uniovi.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class AddMarkFormValidator implements Validator {
	
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Mark mark = (Mark) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "score", "Error.empty");
		if (mark.getScore() < 0 || mark.getScore() > 10) {
			errors.rejectValue("score", "Error.score.values");
		}
		if (mark.getDescription().length() < 5 || (mark.getDescription().length() > 24)) {
			errors.rejectValue("description", "Error.signup.description.length");
		}
		
		
	}
}
