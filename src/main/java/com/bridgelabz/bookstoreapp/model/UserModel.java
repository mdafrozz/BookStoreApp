package com.bridgelabz.bookstoreapp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstoreapp.dto.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class UserModel {
	
	@Id
	@GeneratedValue
	int userId;
	String firstName;
	String lastName;
	String address;
	@Column(name = "email")
	String emailAddress;
	LocalDate DOB;
	String password;
	
	public UserModel(UserDTO userdto) {
			
		this.firstName = userdto.getFirstName();
		this.lastName = userdto.getLastName();
		this.address = userdto.getAddress();
		this.emailAddress = userdto.getEmailAddress();
		DOB = userdto.getDOB();
		this.password = userdto.getPassword();
	}
	
	

}
