package com.videoco.eecs3311.project;

import java.util.ArrayList;
import java.util.UUID;

public class NormalUser extends User {
	private ArrayList<UserOrder> orders;
	int loyaltyPoints;
	String province;
	boolean allOrdersDelivered;
	public NormalUser(UUID userID, String username, String password, String email) {
		this(userID,username,password,email,"Ontario",0);
		
	}
	public NormalUser(UUID userID, String username, String password, String email, String province, int points) {
		this(userID,username,password,email,province,points,true);
		
	}
	public NormalUser(UUID userID, String username, String password, String email, String province) {
		this(userID,username,password,email,province,0);
	}
	public NormalUser(UUID userID, String username, String password, String email, String province, Boolean bool) {
		this(userID,username,password,email,province,0,bool);
	}
	public NormalUser(UUID userID, String username, String password, String email, String province, int points, Boolean bool) {
		super(userID,username,password,UserType.normalUser,email);
		orders= new ArrayList<UserOrder>();
		this.province=province;
		this.loyaltyPoints=points;
		this.allOrdersDelivered=bool;
	}


	public NormalUser(String[] arr) {
		super(arr);
	}

	@Override
	public User clone() {
		User clone= new NormalUser(getUserID(),getUsername(),getPassword(),getEmail());
		return clone;
	}
	
	public void addOrder(UserOrder order) {
		if(this.orders!=null) {
		orders.add(order);
		updateAllDelievered();
		}else {
			orders= new ArrayList<UserOrder>();
			orders.add(order);
			updateAllDelievered();
		}
		
	}
	
	public boolean isAllOrdersDelivered() {
		return allOrdersDelivered;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}
	private void updateAllDelievered() {
		for(Order order:orders) {
			if(!order.getOrderStatus().equals(OrderStatus.Delivered)) {
				this.allOrdersDelivered=false;
			}
		}
	}
	
	public void addMovieToOrder(Movie movie) {
		if(orders.size()>0) {
			orders.get(orders.size()-1).addToOrder(movie.getId());;
		}else {
			UserOrder newOrder= new UserOrder(UUID.randomUUID(),this);
			newOrder.addToOrder(movie.getId());
			orders.add(newOrder);
		}
	}
	
	public Order getOrder(UUID orderID) {
		for(UserOrder order: orders) {
			if(order.getOrderID().equals(orderID)) {
				return order;
			}
		}
		return null;
	}
	
	public ArrayList<UserOrder> getOrders(){
		return orders;
	}
	
	public void setLoyaltyPoints(int i) {
		loyaltyPoints=i;
	}
	
	public void cancelOrder(UUID userOrder) {
		int i=0;
		for(UserOrder order:orders) {
			if(order.getOrderID().equals(userOrder)) {
					orders.remove(i);
					break;
			}
			i++;
		}
	}
	@Override
	public String toString() {
		String s= super.toString();
		s+= " with orders  " + orders.toString();
		return s;
	}
	
	


}