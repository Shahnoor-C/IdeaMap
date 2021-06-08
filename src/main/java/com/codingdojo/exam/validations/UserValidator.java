package com.codingdojo.exam.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.codingdojo.exam.models.User;
import com.codingdojo.exam.services.MainService;

@Component
public class UserValidator implements Validator {
	private final MainService mainService;
	
	public UserValidator(MainService mainService) {
		this.mainService = mainService;
	}
	
	
	 // 1
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    // 2
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match");
        }
        //we can talk to the service and see if any email matches the user from form email 
        if(this.mainService.findByEmail(user.getEmail().toLowerCase()) != null) {
            errors.rejectValue("email", "DupeEmail");

        }
    
    }
    
    

}