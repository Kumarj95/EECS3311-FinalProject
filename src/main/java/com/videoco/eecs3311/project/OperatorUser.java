package com.videoco.eecs3311.project;

import java.util.ArrayList;
import java.util.UUID;

public class OperatorUser extends User {
	ArrayList<PhoneOrder> orders;
	public OperatorUser(UUID userID, String username, String password, String email) {
		super(userID,username,password,UserType.operatorUser,email);
		orders= new ArrayList<PhoneOrder>();
	}
	public OperatorUser(String[] arr) {
		super(arr);
	}
	
	@Override
	public User clone() {
		User clone= new OperatorUser(getUserID(),getUsername(),getPassword(),getEmail());
		return clone;
	}
	
	@Override
	public String toString() {
		String s= super.toString();
		s+= " with orders  " + orders.toString();
		return s;
	}
	
	
	public void addOrder(PhoneOrder norder) {
		boolean isInOrders=false;
		for(PhoneOrder order:orders) {
			if(order.getOrderID().equals(norder.getOrderID())) {
				isInOrders=true;
			}
		}
		if(!isInOrders) {
			orders.add(norder);
		}
	}


}
