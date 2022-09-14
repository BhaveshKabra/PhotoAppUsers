package org.bhavesh.photoapp.api.users.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestModel {
	@NotNull(message = "FirstName cannot be Null")
	@Size(min=4,max=20,message = "FirstName should be in between 4 and 20 characters")
	private String firstName;
	@NotNull(message = "LastName cannot be Null")
	@Size(min=4,max=20,message = "LastName should be in between 4 and 20 characters")
	private String lastName;
	@NotNull(message = "Password cannot be Null")
	@Size(min=8,max=16,message = "Password should be in between 4 and 20 characters")
	private String password;

	@NotNull(message = "LastName cannot be Null")
	@Email
	private String email;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
