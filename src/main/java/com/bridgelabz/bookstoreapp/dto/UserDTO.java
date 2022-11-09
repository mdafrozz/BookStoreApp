package com.bridgelabz.bookstoreapp.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message="Invalid First Name(First Letter Should be in Upper Case and min 3 Characters.)")
	String firstName;
    
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{1,}$", message="Invalid Last Name(First Letter Should be in Upper Case")
	String lastName;
    
	@NotEmpty(message = "Address Cannot be Empty")
    String address;

	@NotNull(message = "Email Address cannot be Null")
	String emailAddress;
	
	@JsonFormat(pattern = "dd MM yyyy")
	@NotNull(message = "Start Date cannot be Empty")
	@PastOrPresent(message = "Start Date should be past or present date")
	LocalDate DOB;
    
	@NotEmpty(message = "Password Cannot be Empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&*()-+=])([a-zA-Z0-9@._-]).{8,}$", message="Invalid Password\n(1.Must have atleast one upper case character.\n" +
            "2.Must contain atleast one numeric value.\n3.Must contain a special symbol.\n4. Must have a lower case character. \n5.Should have minimum 8 characters.)")
    String password;

}
