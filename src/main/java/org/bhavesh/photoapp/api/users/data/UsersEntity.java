package org.bhavesh.photoapp.api.users.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="users")
public class UsersEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1116409363683123430L;
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false,length = 50)
	private String firstName;
	@Column(nullable = false,length = 50)
	private String lastName;
	@Column(nullable = false,length = 80,unique = true)
	private String email;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(nullable = false,unique = true)
	private String userid;
	@Column(nullable = false,unique = true)
	private String encryptedPassword;
	private String password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
