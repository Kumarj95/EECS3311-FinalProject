package com.videoco.eecs3311.project;

import java.util.UUID;

public class AdminUser extends User {
	public AdminUser(UUID userID, String username, String password, String email) {
		super(userID,username,password,UserType.adminUser,email);
	}
	
	public AdminUser(String[] arr) {
		super(arr);
	}
	@Override
	public User clone() {
		User clone= new AdminUser(getUserID(),getUsername(),getPassword(),getEmail());
		return clone;
	}


}
