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
	
	public PhoneOrder getOrder(UUID id) {
		for(PhoneOrder order:orders) {
			if(order.getOrderID().equals(id)) {
				return order;
			}
		}
		return null;
	}
	
	public PhoneOrder getOrderGeneral(UUID id) {
		SystemV sys= SystemV.getInstance();
		ArrayList<PhoneOrder> phoneOrders=sys.getPhoneOrders();
		for(PhoneOrder phoneOrder:phoneOrders) {
			if(phoneOrder.getOrderID().equals(id)) {
				return phoneOrder;
			}
		}
		return null;
	}
    public ArrayList<Movie> search(ArrayList<String> s){
        SystemV sys= SystemV.getInstance();
        ArrayList<Movie> ret= new ArrayList<Movie>();
        for(Movie movie:sys.getMovies()) {
        	if(s.contains(movie.getTitle())) {
        		ret.add(movie);
        	}
        }
        return ret;
    }
    




}
