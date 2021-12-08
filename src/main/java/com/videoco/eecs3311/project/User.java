package com.videoco.eecs3311.project;

import java.util.ArrayList;
import java.util.UUID;

public abstract class User {
	private String username;
	private String password;
	private UserType usertype;
	private UUID userID;
	private String email;
	
	public User(UUID userID,String username, String password, UserType usertype, String email) {
		this.username=username;
		this.password=password;
		this.usertype=usertype;
		this.userID=userID;
		this.email=email;
	}
	public User(UUID userID, String username, String password, String usertype, String email) {
		this.username=username;
		this.password=password;
		try {
			this.usertype=UserType.valueOf(usertype);
		}catch(IllegalArgumentException e) {
			java.lang.System.out.println("Not valid UserType");
		}
		this.userID=userID;
		this.email=email;
	}
	protected User(String []arr) {
		this(UUID.fromString(arr[0]), arr[1], arr[2],arr[4],arr[3]);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;

	}
	public UserType getUsertype() {
		return usertype;
	}
	public UUID getUserID() {
		return userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "This is a user of type " +usertype + " with username " + username +" with id: " + userID;
	}
	public abstract User clone();
	
	
	

}
