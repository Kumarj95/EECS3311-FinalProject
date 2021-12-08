package com.videoco.eecs3311.project;

import java.util.UUID;

public class AdminUser extends User {
//	private static int adminNumber=2;
	public AdminUser(UUID userID, String username, String password, String email) {
		super(userID,username,password,UserType.adminUser,email);
//		adminNumber++;
	}
	
	public AdminUser(String[] arr) {
		super(arr);
	}
	@Override
	public User clone() {
		User clone= new AdminUser(getUserID(),getUsername(),getPassword(),getEmail());
		return clone;
	}
	
	public boolean addMovie(Movie movie) {
		SystemV sys= SystemV.getInstance();
		return sys.addMovie(movie);
	}
	
	public boolean removeMovie(UUID id) {
		SystemV sys= SystemV.getInstance();
		return sys.removeMovie(id);
	}
	public boolean removeMovie(Movie id) {
		SystemV sys= SystemV.getInstance();
		return sys.removeMovie(id);
	}
	
	public boolean updateMovieInfo(UUID info, Movie movie) {
		SystemV sys= SystemV.getInstance();
		return sys.updateMovie(info, movie);
	}
	
	public boolean removeAccount(UUID id) {
		SystemV sys= SystemV.getInstance();
		return sys.removeNormalUser(id);
	}
	
	public boolean updateUser(UUID id, NormalUser user) {
		SystemV sys= SystemV.getInstance();
		return sys.updateNormalUser(id, user);
		
	}



}
